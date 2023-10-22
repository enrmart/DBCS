import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Vino } from './app.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ClienteApiRestService {
private static readonly BASE_URI = 'http://localhost:8080/ejemploVinos';

constructor(private http: HttpClient) { } // inyectamos el servicio HttpClient

   //Ejemplo de llamada retornando el cuerpo de la respuesta
   getAllVinos() {
     console.log("dentro de getAllVInos");
     let url = ClienteApiRestService.BASE_URI;
     return this.http.get<Vino[]>(url); // Retorna el cuerpo de la respuesta
   }

   // Ejemplo de llamada retornando toda la respuesta
   getAllVinos_ConResponse(): Observable<HttpResponse<Vino[]>> {
     let url = ClienteApiRestService.BASE_URI;
     return this.http.get<Vino[]>(url, { observe: 'response' });
   }

   borrarVino(id: String): Observable<HttpResponse<any>> {
     let url = ClienteApiRestService.BASE_URI + id;
     return this.http.delete(url, { observe: 'response', responseType: 'text'});
   }

   anadirVino(vino: Vino): Observable<HttpResponse<any>> {
     let url = ClienteApiRestService.BASE_URI;
     return this.http.post(url, vino, { observe: 'response', responseType: 'text'});
   }

   modificarPrecio(id: String, vino: Vino): Observable<HttpResponse<any>> {
     let url = ClienteApiRestService.BASE_URI + id;
     return this.http.put(url, vino, { observe: 'response', responseType: 'text'});
   }

   getVino(id: String): Observable<HttpResponse<Vino>> {
     let url = ClienteApiRestService.BASE_URI + id;
     return this.http.get<Vino>(url, { observe: 'response' });
   }
}


