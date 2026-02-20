import { Component, OnInit } from '@angular/core';
import {CommonModule} from '@angular/common'; //Necesario para *ngFor y *ngIf
//Ajusta la ruta de importación si es necesario, debe ser así:
import { OrdenService } from './services/orden.service';
import { Orden } from './models/orden.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})

export class App implements OnInit {
  listaOrdenes: Orden[] = [];
  cargando = true;

  constructor(private ordenService: OrdenService) { }

  ngOnInit(): void {
    this.cargarOrdenes();
  }

  cargarOrdenes(){
    this.ordenService.getOrdenes().subscribe({
      next: (datos) => {
        console.log('Datos recibidos:', datos);
        this.listaOrdenes = datos;
        this.cargando = false;
      },
      error: (error) => {
        console.error('Error conectado con Spring Boot:', error);
        this.cargando = false;
      }
    });
  }

}
