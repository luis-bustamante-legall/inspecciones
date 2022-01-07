import 'dart:io';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:legall_rimac_virtual/blocs/blocs.dart';
import 'package:legall_rimac_virtual/localizations.dart';
import 'package:legall_rimac_virtual/models/models.dart';
import 'package:legall_rimac_virtual/repositories/repositories.dart';
import 'package:legall_rimac_virtual/resource_cache.dart';
import 'package:legall_rimac_virtual/routes.dart';
import 'package:legall_rimac_virtual/widgets/chat_button.dart';
import 'package:legall_rimac_virtual/widgets/image_card.dart';
import 'package:image_picker/image_picker.dart';
import 'package:legall_rimac_virtual/widgets/phone_call_button.dart';
import 'package:path_provider/path_provider.dart';
import 'package:video_player/video_player.dart';
import 'package:open_file/open_file.dart';

class InspectionStep1Screen extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => InspectionStep1ScreenState();
}

class InspectionStep1ScreenState extends State<InspectionStep1Screen> {
  ImagePicker _picker = ImagePicker();
  SettingsRepository _settingsRepository;
  VideoBloc _videoBloc;
  ResourceCache _resourceCache = ResourceCache();


  Widget _videoPlayer({String resourceUrl, String cache}) {
    if (resourceUrl == null)
      return null;
    VideoPlayerController _playerController;
    if (cache != null) {
      _playerController = VideoPlayerController.file(File(cache));
    }
    else {
      _playerController = VideoPlayerController.network(resourceUrl);
    }
    _playerController.initialize();
    return Container(
      height: 180,
      color: Colors.black,
      child: Transform.scale(
          scale: 1 / _playerController.value.aspectRatio,
          child:Center(
              child: AspectRatio(
                aspectRatio: _playerController.value.aspectRatio,
                child: VideoPlayer(_playerController),
              )
          )
      )
    );
  }

  IconData _iconFromStatus(ResourceStatus status) {
    switch(status) {
      case ResourceStatus.empty:
        return Icons.add;
      case ResourceStatus.uploaded:
        return Icons.update;
      case ResourceStatus.approved:
        return Icons.check_circle;
      default:
        return Icons.cancel;
    }
  }

  Color _colorFromStatus(ResourceStatus status) {
    switch(status) {
      case ResourceStatus.empty:
        return Colors.indigo;
      case ResourceStatus.uploaded:
        return Colors.amber;
      case ResourceStatus.approved:
        return Colors.green;
      default:
        return Colors.red;
    }
  }
  
  @override
  void initState() {
    _settingsRepository = RepositoryProvider.of<SettingsRepository>(context);
    _videoBloc = BlocProvider.of<VideoBloc>(context);
    _videoBloc.add(LoadVideo(
        _settingsRepository.getInspectionId()));
    super.initState();

  }

  @override
  Widget build(BuildContext context) {
    ThemeData _t = Theme.of(context);
    AppLocalizations _l = AppLocalizations.of(context);

    return Scaffold(
      appBar: AppBar(
        title: Text(_l.translate('inspection step',arguments: { "step": "1" })),
        actions: [
          PhoneCallButton(),
          ChatButton()
        ],
      ),
      body: BlocListener<VideoBloc,VideoState>(
        listener: (context,state) {
          if (state is VideoUploadCompleted) {
            if (!state.success) {
              Future.delayed(Duration(milliseconds: 100),() {
                var messenger = Scaffold.of(context);
                messenger.hideCurrentSnackBar();
                messenger.showSnackBar(SnackBar(
                  duration: Duration(seconds: 4),
                  backgroundColor: Colors.red,
                  content: ListTile(
                    leading: Icon(Icons.announcement_rounded),
                    title: Text(_l.translate('problems uploading videos'),
                      overflow: TextOverflow.ellipsis,
                    ),
                  ),
                ));
              });
            }
          }
        },
        child: BlocBuilder<VideoBloc,VideoState>(
          builder: (context,state) {
            var videos = <VideoModel>[];
            var uploadingVideos = <String>[];
            if (state is VideoLoaded || state is VideoUploading) {
              if (state is VideoLoaded) {
                if (state.success) {
                  videos = state.videos;
                } else {
                  Future.delayed(Duration(milliseconds: 100),() {
                    var messenger = Scaffold.of(context);
                    messenger.hideCurrentSnackBar();
                    messenger.showSnackBar(SnackBar(
                      duration: Duration(seconds: 4),
                      backgroundColor: Colors.red,
                      content: ListTile(
                        leading: Icon(Icons.announcement_rounded),
                        title: Text(_l.translate('problems loading video'),
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                    ));
                  });
                }
              } else if (state is VideoUploading) {
                videos = state.videos;
                uploadingVideos = state.uploading;
              }
            }
            return ListView(
              padding: EdgeInsets.all(20),
              children: [
                Text(_l.translate('load video 360')),
                SizedBox(height: 20,),
                Column(
                  children: videos.map((video) =>
                      SizedBox(
                        height: 250,
                        child: ImageCard(
                          child: video.thumbnailUrl == null && video.resourceUrl != null?
                          _videoPlayer(
                              resourceUrl: video.resourceUrl
                          ):null,
                          image: video.thumbnailUrl != null ?
                          CachedNetworkImageProvider(
                            video.thumbnailUrl,
                            cacheKey: 'video_${video.id}_${video.dateTime?.millisecondsSinceEpoch}'
                          ): null,
                          working: uploadingVideos.contains(video.id),
                          title: Text(video?.description??'',
                            style: _t.textTheme.button,
                          ),
                          icon: _iconFromStatus(video.status),
                          color: _colorFromStatus(video.status),
                          emptyIcon: Icons.videocam,
                          onHelp: () async {
                            VideoPlayerController playerController =
                            await _resourceCache.loadVideoHelp(video.helpExampleUrl);
                            playerController.initialize();
                            playerController.setLooping(true);
                            playerController.play();
                            showDialog(
                                context: context,
                                builder: (_) => AlertDialog(
                                  title: Text(_l.translate('how take video 360')),
                                  content: Column(
                                    mainAxisSize: MainAxisSize.min,
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Text(video.helpText??''),
                                      SizedBox(height: 10),
                                      Text(_l.translate('example'),
                                        style: _t.textTheme.button,
                                      ),
                                      SizedBox(height: 10),
                                      Transform.scale(
                                          scale: 1 / playerController.value.aspectRatio,
                                          child:Center(
                                              child: AspectRatio(
                                                aspectRatio: playerController.value.aspectRatio,
                                                child: VideoPlayer(playerController),
                                              )
                                          )
                                      )
                                    ],
                                  ),
                                  actions: <Widget>[
                                    FlatButton(
                                      child: Text(_l.translate('accept')),
                                      onPressed: () {
                                        Navigator.of(context).pop();
                                      },
                                    )
                                  ],
                                ));
                          },
                          onTap: () async {
                            if (video.status == ResourceStatus.empty ||
                                video.status == ResourceStatus.rejected) {
                              var videoPicker = await _picker.getVideo(
                                  source: ImageSource.camera,
                                  maxDuration: Duration(seconds: 20)
                              );
                              if (videoPicker != null) {
                                _videoBloc.add(UploadVideo(
                                    video, File(videoPicker.path)));
                              }
                            } else {
                              try {
                                var appDir = await getApplicationDocumentsDirectory();
                                await for (var file in appDir.list()) {
                                  if (file.path.contains(video.id)) {
                                    await OpenFile.open(file.path);
                                    return;
                                  }
                                }
                              } catch(ex) {
                                print(ex.toString());
                              }
                            }
                          },
                        ),
                      )
                  ).toList(),
                ),
                SizedBox(height: 50,),
                Visibility(
                    visible: !videos.any((video) => video.status != ResourceStatus.approved),
                    child: Align(
                        alignment: Alignment.centerRight,
                        child: RaisedButton(
                          color: _t.accentColor,
                          child: Text(_l.translate('continue').toUpperCase(),
                            style: _t.accentTextTheme.button,
                          ),
                          onPressed: () async {
                            Navigator.pushNamed(context, AppRoutes.inspectionStep2);
                          },
                        )
                    )
                )
              ],
            );
          },
        )
      )
    );
  }
}