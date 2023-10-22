import { Component, OnInit } from '@angular/core';
import { Vino } from '../shared/app.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteApiRestService } from '../shared/cliente-api-rest.service';
import { Observable } from 'rxjs';
import { DataService } from '../shared/data.service';

@Component({
selector: 'app-editar-vino',
templateUrl: './editar-vino.component.html',
styleUrls: ['./editar-vino.component.css']
})

export class EditarVinoComponent implements OnInit {
  vinoVacio = {bodega_id: 0,categoria: "",precio: 0.0,denominacion: "",id: 0,nombre_comercial: ""};
  vino = this.vinoVacio as Vino;  // Hay que darle valor inicial, si no salta una
                                  //excepcion en el template (html), ya que solo en el
                                  //caso de editar se le daría valor inicial a la variable

  // Ponemos el simbolo "!" para evitar el error en compilacion debido a la no incializacion
  // Van a tener valor seguro, asi que no hay problema en no inicializarlo
  id!: String; // para guardar el id del vino a editar
  operacion!: String; // para guardar la operación (añadir/editar) a realizar

    //Inyectamos:
   //  * ruta activa, mediante ActivatedRoute
   //  * servicio de enrutamiento Router
   //  * nuestro servicio de acceso al servicio REST cliente-api-rest
   //  * nuestro servicio para compartir mensajes entre componentes

   constructor(private ruta: ActivatedRoute, private router: Router, private clienteApiRest: ClienteApiRestService, private datos: DataService) { }
   ngOnInit() {
       console.log("En editar-vino");
       // Operacion: va en el ultimo string (parte) de la URL
   this.operacion = this.ruta.snapshot.url[this.ruta.snapshot.url.length- 1].path;
   if (this.operacion == "editar") { // Si la operacion es editar se captura el id de la URL
                                     //y se trae el json con el vino, para mostrarlo en el
                                     //HTML. Si no es “editar”, será “nuevo” y la operacion de
                                     //traer vino no se realizara y el formulario aparecerá vacio
     console.log("En Editar");
     this.ruta.paramMap.subscribe( // Capturamos el id de la URL
        params => {
          this.id = params.get('id')!;  // Se usa "!" para evitar error en compilacion.
                                        // No va a ser null nunca
        },
        err => console.log("Error al leer id para editar: " + err))
      // console.log("Id: " + this.id);
    this.clienteApiRest.getVino(this.id).subscribe( // Leemos de la base de datos vía API
       resp => {
          this.vino = resp.body!;  // No comprobamos “status”. El vino que existe seguro
                                // Se usa “!” por la misma razón que antes
       },
       err => {
          console.log("Error al traer el vino: " + err.message);
          throw err;
       })
      }
      }
//Se ejecuta al realizar en el template la acción de enviar el formulario
onSubmit() {
 console.log("Enviado formulario");
 if (this.id) { // si existe id estamos en edicion, si no en añadir
 //console.log("Nuevo valor del precio: " + this.vino.precio);
 this.clienteApiRest.modificarPrecio(String(this.vino.id), this.vino).subscribe(
 resp => {
 if (resp.status < 400) { // Si no hay error en la operacion por parte del servicio
 this.datos.cambiarMostrarMensaje(true);
 this.datos.cambiarMensaje(resp.body); // Mostramos el mensaje enviado por el API
 } else {
 this.datos.cambiarMostrarMensaje(true);
 this.datos.cambiarMensaje("Error al modificar comentario");
 }
 this.router.navigate(['vinos']); // Volvemos a la vista 1 (listado de vinos)
 },
 err=> {
 console.log("Error al editar: " + err.message);
 throw err;
 })
} else { // Parte añadir vino
this.clienteApiRest.anadirVino(this.vino).subscribe(
 resp => {
 if (resp.status < 400) {
 this.datos.cambiarMostrarMensaje(true);
 this.datos.cambiarMensaje(resp.body); // Mostramos el mensaje retornado por el API
 } else {
 this.datos.cambiarMostrarMensaje(true);
 this.datos.cambiarMensaje("Error al añadir vino");
 }
 this.router.navigate(['vinos']);
 },
 err=> {
 console.log("Error al editar: " + err.message);
 throw err;
 })
}
}
}
