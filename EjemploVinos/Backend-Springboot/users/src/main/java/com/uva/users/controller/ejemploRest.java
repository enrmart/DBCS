package com.uva.users.controller;

import com.uva.users.exception.VinoException;
import com.uva.users.model.Bodega;
import com.uva.users.model.Vino;
import com.uva.users.model.VinoConRelacion;
import com.uva.users.repository.BodegaRepository;
import com.uva.users.repository.VinoConRelacionRepository;
import com.uva.users.repository.VinoRepository;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ejemploVinos")
@CrossOrigin(origins = "*")
public class ejemploRest{
    private final VinoRepository repository;
    private final VinoConRelacionRepository repository2;
    private final BodegaRepository repository3;


    public ejemploRest(VinoRepository repository, VinoConRelacionRepository repository2, BodegaRepository repository3) {
        this.repository = repository;
        this.repository2=repository2;
        this.repository3=repository3;
    }

    // No se ha incluido el atributo path. Si en el ejemplo que tienes está,3
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newVino(@RequestBody Vino newVino){
        try { repository.save(newVino);
            return "Nuevo registro creado";
        } catch (Exception e) {
            // Se deja esta parte comentada como alternativa a la gestión de errores propuesta
            //throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error alcrear el nuevo registro.");
            // Se usa este sistema de gestión de errores porque es más sencillo hacer que el cliente reciba el mensaje de error
            throw new VinoException("Error al crear el nuevo registro.");
        }
    }

    //          EJERCICIO 1 1.3
    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Vino> getVinos() {
         return repository.findAll();
    }

    //          EJERCICIO 2 1.3
    @GetMapping(value="/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
    public Vino getVinoById(@PathVariable("id") Integer id) {
        Vino vino = repository.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        return vino;
    }
    //          EJERCICIO 3 1.3
    @DeleteMapping(value="/{id}")
    public String deleteVinoById(@PathVariable("id") Integer id) {
        try {
            repository.deleteById(id);
            return "El vino con id "+ id +" se ha borrado correctamente" ;
        } catch (Exception e) {
            // Se deja esta parte comentada como alternativa a la gestión de errores propuesta
            //throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error alcrear el nuevo registro.");
            // Se usa este sistema de gestión de errores porque es más sencillo hacer que el cliente reciba el mensaje de error
            throw new VinoException("Error al borrar el vino con id: "+id);
        }
    }
    //          EJERCICIO 4 1.3
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public String putVinos(@RequestBody Vino uVino, @PathVariable("id") Integer id){
        Vino vino = repository.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        vino.setCategoria(uVino.getCategoria());
        vino.setBodega_id(uVino.getBodega_id());
        vino.setCategoria(uVino.getCategoria());
        vino.setPrecio(uVino.getPrecio());
        vino.setNombre_comercial(uVino.getNombre_comercial());
        repository.save(vino);
        return "El vino ha sido actualizado correctamente";
    }

    //          EJERCICIO 5 1.3
    @PatchMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public String patchVino(@RequestBody Vino uVino, @PathVariable("id") Integer id){
        Vino vino = repository.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        vino.setPrecio(uVino.getPrecio());
        repository.save(vino);
        return "El vino ha sido patcheado correctamente";
    }


    //          EJERCICIO 1 PARTE 1.2
    @DeleteMapping(value={"/{cartaVinos}/{vino}"})
    public String deleteVinoFromCarta(@PathVariable("cartaVinos") String carta,@PathVariable("vino") String vino) {
        return "El vino eliminado es " + vino + " de la carta " + carta;
    }

    //          EJERCICIO 2 y 3 PARTE 1.2
    @GetMapping(value={"/cartaVinos/{vino}/{anno}","/cartaVinos/{vino}","/cartaVinos"})
    public String getVinoFromCarta(@PathVariable(required=false) Map<String,String> pathVariables) {
        String vino = pathVariables.get("vino");
        String anno = pathVariables.get("anno");
        if (vino != null && anno!=null) {
            return "El vino buscado es " + vino + " de la cosecha " + anno;
        } else{
            return "Faltan Parametros";
        }
    }

    //          EJERCICIO 4 PARTE 1.2
    @GetMapping("/cartaVinos/{id}")
    public String getCartaConQuery(@PathVariable("id") Long id,@RequestParam(defaultValue="30") List<String> campos){
        switch(campos.size()){
            case 3:
                String precio = campos.get(0);
                String denominacion = campos.get(1);
                String bodega = campos.get(2);
                return "El id del vino es " + id + " su precio es " + precio + " su denominacion es " + denominacion + " su bodega es " + bodega;
            case 2:
                try{
                    return "El id del vino es " + id + " su precio es " +Integer.parseInt(campos.get(0))+ " su denominacion o bodega es "+ campos.get(1);
                }catch(NumberFormatException e){
                    return "El id del vino es "+ id + " su denominacion es "+ campos.get(0) + " su bodega es "+ campos.get(1);
                }
            default:
                try{
                    return "El id del vino es " + id + " su precio es " + Integer.parseInt(campos.get(0));
                }catch(NumberFormatException e){
                    return "El id del vino es "+ id + " su denominacion o bodega es "+ campos.get(0);
                }
        }
    }

    @GetMapping(value="/VinoPorNombre/{nombre}")
    public Vino getVinoPorNombre_Comercial(@PathVariable String nombre){
        Vino vino = repository.findByNombreComercial(nombre).orElseThrow(()->
                new VinoException("no se ha encontrado el vino de nombre " + nombre));
        return vino;
    }
    @GetMapping("/VinoPorPrecio")
    public List<Vino> getVinoPorPrecio(@RequestParam Float precio1, @RequestParam Float precio2){
        List<Vino> vinos = repository.findByPrecioBetween(precio1, precio2);
        return vinos;
    }
    //      EJERCICIO 1 1.4
    @DeleteMapping(value="/BorrarPorDenominacion_Categoria",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vino> deletePorDenominacion_Categoria(@RequestBody String json){
        List <Vino> borrados = new ArrayList<>();
        try {
            JSONObject jsonObjeto = new JSONObject(json);
            String denominacion = jsonObjeto.getString("denominacion");
            String categoria = jsonObjeto.getString("categoria");
            boolean existe = repository.existsVinoByDenominacionAndCategoria(denominacion,categoria);
            if (existe){
                borrados = repository.deleteByDenominacionAndCategoria(denominacion, categoria);
                return borrados;
            } else {
                System.out.println("No existen vinos de la categoría y denominación");
            }
        } catch (Exception e) {
            System.out.println(e);
            return borrados;
        }
        return null;
    }
    //              EJERCICIO 2 1.4
    @GetMapping(value="/VinoPorDenominacion/{denominacion}")
    public String getVinoPorDenominacion(@PathVariable("denominacion") String denominacion){
        Long vinos = repository.countByDenominacion(denominacion);
        return "El numero de vinos de la denominacion "+denominacion+" es "+vinos;
    }

    //              EJERCICIO 3 1.4
    @GetMapping(value="/VinoPorDistintaDenominacion/{denominacion}")
    public String getVinoDistintaDenominacion(@PathVariable("denominacion") String denominacion){
        Long vinos=repository.countByDenominacionNot(denominacion);
        return "El numero de vinos que no son de la denominacion "+denominacion+" es "+vinos;
    }

    //              EJERCICIO 4 1.4
    @PostMapping(value="/VinosConRelacion",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newVinoConRelacion(@RequestBody VinoConRelacion newVinoR){
        try {
            repository2.save(newVinoR);
            return "Nuevo VinoConRelacion creado";
        } catch (Exception e) {
            // Se deja esta parte comentada como alternativa a la gestión de errores propuesta
            //throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error alcrear el nuevo registro.");
            // Se usa este sistema de gestión de errores porque es más sencillo hacer que el cliente reciba el mensaje de error
            throw new VinoException("Error al crear el nuevo VinoConRelacion.");
        }
    }

    @PostMapping(value="/VinosConRelacion/Bodega",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newBodega(@RequestBody Bodega bodega){
        try {
            repository3.save(bodega);
            return "Nueva Bodega creado";
        } catch (Exception e) {
            // Se deja esta parte comentada como alternativa a la gestión de errores propuesta
            //throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error alcrear el nuevo registro.");
            // Se usa este sistema de gestión de errores porque es más sencillo hacer que el cliente reciba el mensaje de error
            throw new VinoException("Error al crear la nueva Bodega.");
        }
    }

    @GetMapping(value="/VinosConRelacion/{id}")
    public VinoConRelacion getVinoConRelacion(@PathVariable("id") Integer id){
        VinoConRelacion vino = repository2.findById(id).orElseThrow(()-> new VinoException("no se ha encontrado el vino con Relacion con id: " + id));
        return vino;
    }

    @GetMapping(value="/VinosConRelacion/Bodega/{id}")
    public Bodega getBodega(@PathVariable("id") Integer id){
        Bodega bodega = repository3.findById(id).orElseThrow(()-> new VinoException("no se ha encontrado la bodega con id: " + id));
        return bodega;
    }

    //              EJERCICIO 5 1.4
    @GetMapping(value="/VinosConRelacion")
    public List<VinoConRelacion> getVinoRPorDenominacionyBodega(@RequestParam String denominacion,@RequestParam String bodega){
        List<VinoConRelacion> vinos = repository2.findByDenominacionYBodega(denominacion,bodega);
        return vinos;
    }

    //          EJERCICIO 6 1.4
    @PutMapping(value="/VinosConRelacion/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public String putVinoConRelacion(@RequestBody VinoConRelacion uVino, @PathVariable("id") Integer id){
        VinoConRelacion vino = repository2.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        vino.setCategoria(uVino.getCategoria());
        vino.setBodegaId(uVino.getBodegaId());
        vino.setNombreComercial(uVino.getNombreComercial());
        vino.setPrecio(uVino.getPrecio());
        vino.setDenominacion(uVino.getDenominacion());
        repository2.save(vino);
        return "El vino con relacion ha sido actualizado correctamente";
    }


    //          EJERCICIO 6 1.4
    @PutMapping(value="/VinosConRelacion/Bodega/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public String putBodega(@RequestBody Bodega uBodega, @PathVariable("id") Integer id){
        Bodega bodega = repository3.findById(id).orElseThrow(() -> new VinoException("Sin resultado"));
        bodega.setCif(uBodega.getCif());
        bodega.setDireccion(uBodega.getDireccion());
        bodega.setNombre(uBodega.getNombre());
        bodega.setVinoCollection(uBodega.getVinoCollection());
        repository3.save(bodega);
        return "La bodega ha sido actualizada correctamente";
    }
}
