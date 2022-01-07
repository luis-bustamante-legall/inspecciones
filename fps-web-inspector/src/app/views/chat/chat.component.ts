import { Component, Input, OnInit } from '@angular/core';
import { faComment } from '@fortawesome/free-solid-svg-icons';
import { Chats } from 'src/app/models/collections/chats';
import { FirebaseService } from 'src/app/services/firebase.service';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  @Input() inspectionsId: string;
  @Input() estado: boolean;
  faComment = faComment;
  mensaje: string;
  elemento: any;
  chats: Chats[] = [];

  constructor(public firebaseService: FirebaseService) {}

  ngOnInit(): void {
    this.elemento = document.getElementById('app-mensajes');
    setTimeout(() => {
      this.cargarMensaje();
    }, 3000);
  }

  enviarMensaje() {
    if (this.mensaje.length != 0) {
      this.firebaseService.sendMessage(this.mensaje, this.inspectionsId)
        .then(() => this.limpiarEntrada())
        .catch((err) => console.error(err));
    }
  }

  cargarMensaje() {
    this.firebaseService.loadChats(this.inspectionsId).subscribe((resp: Chats[]) => {
      this.chats = resp;
      setTimeout(() => {
        this.elemento.scrollTop = this.elemento.scrollHeight;
      }, 30);
    });
  }

  limpiarEntrada() {
    this.mensaje = "";
  }

}
