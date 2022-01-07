import { Component, OnInit } from '@angular/core';
import { URL_APPSTORE, URL_PLAYSTORE } from 'src/app/utils/constantes';

@Component({
  selector: 'app-redireccion',
  templateUrl: './redireccion.component.html',
  styleUrls: ['./redireccion.component.css']
})
export class RedireccionComponent implements OnInit {

  mensaje: any;
  icono: any;
  link:any;
  constructor() { }

  ngOnInit(): void {
    this.detectar();
  }

  timeLeft: number = 3;
  interval;

startTimer() {
    this.interval = setInterval(() => {
      if(this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        this.timeLeft = 3;
      }
    },1000)
  }

  pauseTimer() {
    clearInterval(this.interval);
  }


  detectar() {
    if (navigator.userAgent.match(/(iPhone|iPod|iPad|Android)/)) {
      if (navigator.userAgent.match(/Android/i)){
        this.icono= '../../../../assets/images/google.png';
        this.mensaje='Redirigiendo...';
        this.link= `${URL_PLAYSTORE}`;
        this.startTimer()
        setTimeout(() => {
          this.pauseTimer();
          window.open(URL_PLAYSTORE, '_blank');
        }, 3000);
      }    
      if(navigator.userAgent.match(/iPhone/i)){
        this.icono= '../../../../assets/images/apple.png';
        this.mensaje='Redirigiendo...';
        this.link= `${URL_APPSTORE}`;
        this.startTimer()
        setTimeout(() => {
          this.pauseTimer();
          window.open(URL_APPSTORE, '_blank');
        }, 3000);
      }
      if(navigator.userAgent.match(/iPad/i)){
        this.icono= '../../../../assets/images/apple.png';
        this.link= `${URL_APPSTORE}`;
        this.startTimer()
        setTimeout(() => {
          this.pauseTimer();
          window.open(URL_APPSTORE, '_blank');
        }, 3000);
      }
      if(navigator.userAgent.match(/iPod/i)){
        this.icono= '../../../../assets/images/apple.png';
        this.link= `${URL_APPSTORE}`;
        this.startTimer()
        setTimeout(() => {
          this.pauseTimer();
          window.open(URL_APPSTORE, '_blank');
        }, 3000);
      }
    }
    else{
      console.log('ABRE DESDE movil')
      this.mensaje = 'Lo sentimos este enlace solo esta disponible desde su dispositivo movil...'
    }
  }

}
