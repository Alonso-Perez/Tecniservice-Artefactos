import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Orden} from '../models/orden.model';

@Injectable({providedIn: 'root'}) //Esto hace que esté disponible en toda la app (como un @Service de Spring)
export class OrdenService {
  private apiUrl = 'http://localhost:8080/api/v1/ordenes';

  constructor(private http: HttpClient) { }

  //Método para obtener todas las órdenes
  getOrdenes(): Observable<Orden[]>{
    return this.http.get<Orden[]>(this.apiUrl);
  }
}
