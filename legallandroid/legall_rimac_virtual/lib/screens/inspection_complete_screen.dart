import 'dart:async';
import 'package:geocoder/geocoder.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:geocoder/services/base.dart';
import 'package:legall_rimac_virtual/base/base_location.dart';
import 'package:legall_rimac_virtual/localizations.dart';
import 'package:legall_rimac_virtual/models/inspection_model.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:legall_rimac_virtual/routes.dart';
import 'package:intl/intl.dart';
import 'package:legall_rimac_virtual/widgets/progress_dialog.dart';


class InspectionCompleteScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => InspectionCompleteScreenState();
}

class InspectionCompleteScreenState extends State<InspectionCompleteScreen>{
  Completer<GoogleMapController> _controller = Completer();
  final Geocoding _geoCoding = Geocoder.local;
  String _addressDesc;
  double latitude = 0.0;
  double longitude = 0.0;


  @override
  Widget build(BuildContext context) {
    final Coordinates coordinates =  ModalRoute.of(context).settings.arguments;
    latitude = coordinates.latitude;
    longitude=coordinates.longitude;
    ThemeData _t = Theme.of(context);
    AppLocalizations _l = AppLocalizations.of(context);

    final CameraPosition _kGooglePlex = CameraPosition(
      target: LatLng(latitude, longitude),
      zoom: 15.23,
    );

    _geoCoding.findAddressesFromCoordinates(Coordinates(latitude, longitude))
    .then((address) {
      if (address.isNotEmpty) {
        setState(() {
          var firstAddress = address.first;
          _addressDesc = "${firstAddress.countryName}, ${firstAddress.addressLine}";
        });
      }
    });

    return Scaffold(
        appBar: AppBar(
          title: Text(_l.translate('inspection completed'))
        ),
        body: ListView(
            padding: EdgeInsets.all(20),
            children: [
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: EdgeInsets.only(left: 15,right: 15),
                    child: Icon(Icons.check_circle_outline,
                      size: 50,
                      color: Colors.green,
                    ),
                  ),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        Text(_l.translate('inspection is complete'),
                            style: _t.textTheme.headline6
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Text(_addressDesc ?? _l.translate('locating'),
                          style: _t.textTheme.subtitle1,

                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Text('${DateFormat('d MMM yyyy, hh:mm a').format(DateTime.now())}',
                            style: _t.textTheme.subtitle1
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(_l.translate('gps coors'),
                          style: _t.textTheme.subtitle2,
                        ),
                        Text('${latitude}, ${longitude}')
                      ],
                    )
                  )
                ],
              ),
              Padding(
                padding: EdgeInsets.only(top: 20, bottom: 20),
                child: SizedBox(
                  height: 400,
                  child: GoogleMap(
                    zoomControlsEnabled: false,
                    scrollGesturesEnabled: false,
                    mapType: MapType.normal,
                    markers: [
                      Marker(
                        markerId: MarkerId('primary'),
                        position: LatLng(latitude, longitude)
                      )
                    ].toSet(),
                    initialCameraPosition: _kGooglePlex,
                    onMapCreated: (GoogleMapController controller) {
                      _controller.complete(controller);
                    },
                  ),
                )
              ),
              Align(
                alignment: Alignment.centerRight,
                child: RaisedButton(
                  child: Text(_l.translate('close').toUpperCase(),
                      style: _t.accentTextTheme.button
                  ),
                  color: _t.accentColor,
                  onPressed: () {
                    Navigator.pushNamedAndRemoveUntil(context,AppRoutes.home,
                    (Route<dynamic> route) => false);
                  },
                ),
              )
            ]
        )
    );
  }

}