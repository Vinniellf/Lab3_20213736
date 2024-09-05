package com.example.tarea2_20213736;

import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.DataOutput;
import java.util.ArrayList;

import static java.lang.Math.max;

@Controller
public class TareaController {
    ArrayList<Auto> lista_auto = new ArrayList<>();
    ArrayList<Seguro> lista_seguro = new ArrayList<>();
    ArrayList<Sede> lista_sede = new ArrayList<>();

     @GetMapping(value = "")
     public String Principal(Model model){
         lista_auto.add(new Auto(1, "Corvet ga", "negro", 0, 2, 200));
         lista_sede.add(new Sede(1, "Jesus Maria", "Diego de Medina"));
         lista_seguro.add(new Seguro( 1, "SegurosPucp", 5000.0, 20));
         model.addAttribute("lista", lista_auto);
        return "listaAutos";
     }
    /***
     //Usamos la manera estándar para traer datos del html
     @PostMapping(value = "/guardarEstandar")
     public String guardarEstandar(@RequestParam("nombre") String nombre,
     @RequestParam("apellido") String apellido,
     @RequestParam("dni") String dni,
     @RequestParam("codigo") String codigo,
     @RequestParam("direccion") String direccion, Model model){
     Persona p = new Persona(nombre, apellido, dni, codigo, direccion);

     System.out.println("Nombre: " + p.getNombre() + "\n" + "Apellido: " + p.getApellido() + "\n" +
     "DNI: " + p.getDni() + "\n" + "Codigo PUCP: " + p.getCodigo());
     lista.add(p);
     return "formulario";
     }

     //Ahora guardaremos usando databinding y usando el objeto Persona
     @PostMapping(value = "/guardar")
     public String guardarDatabinding(Persona p){
     System.out.println("Nombre: " + p.getNombre() + "\n" + "Apellido: " + p.getApellido() + "\n" +
     "DNI: " + p.getDni() + "\n" + "Codigo PUCP: " + p.getCodigo());
     lista.add(p);
     return "formulario";
     }***/

    @GetMapping(value = "/ListaAutos")
    public String MostrarListaAuto(Model model){
        model.addAttribute("lista", lista_auto);
        return "listaAutos";
    }

    @GetMapping(value = "/ListaSedes")
    public String MostrarListaSede(Model model){
        model.addAttribute("lista", lista_sede);
        return "listaSedes";

    }

    @GetMapping(value = "/ListaSeguros")
    public String MostrarListaSeguro(Model model){
        model.addAttribute("lista", lista_seguro);
        return "listaSeguros";

    }

    @GetMapping(value = "/agregarAuto")
    public String MostrarFormAuto(Model model){
        model.addAttribute("listaSede", lista_sede);
        int numero = 1;
        for(Auto auto : lista_auto){
            numero = max(numero, auto.getId());
        }
        model.addAttribute("num", numero + 1);
        return "formularioAuto";

    }

    @GetMapping(value = "/agregarSede")
    public String MostrarFormSede(Model model){
        int numero = 1;
        for(Sede sede : lista_sede){
            numero = max(numero, sede.getId());
        }
        model.addAttribute("numero", numero + 1);
        return "formularioSede";
    }

    @GetMapping(value = "/agregarSeguro")
    public String MostrarFormSeguro(Model model){
        int numero =  1;
        for(Seguro seguro : lista_seguro){
            numero = max(numero, seguro.getId());
        }
        model.addAttribute("num", numero + 1);
        return "formularioSeguro";
    }

    @PostMapping(value = "/meterAuto")
    public String MeterAuto(@RequestParam("id") String id,
                            @RequestParam("modelo") String modelo,
                            @RequestParam("color") String color,
                            @RequestParam("kilometraje") String kilometraje,
                            @RequestParam("sede") String sede,
                            @RequestParam("costo") String costo, Model model){
        lista_auto.add(new Auto(Integer.parseInt(id), modelo, color, Integer.parseInt(kilometraje), Integer.parseInt(sede), Double.parseDouble(costo)));
        model.addAttribute("lista", lista_auto);
        return "listaAutos";
    }

    @PostMapping(value = "/meterSede")
    public String MeterSede(@RequestParam("id") String id,
                            @RequestParam("direccion") String direccion,
                            @RequestParam("distrito") String distrito, Model model){
        Integer ga = Integer.parseInt(id);
        Sede se = new Sede(ga, distrito, direccion);
        lista_sede.add(se);
        model.addAttribute("lista", lista_sede);
        return "listaSedes";
    }

    @PostMapping(value = "/meterSeguro")
    public String MeterSeguro(@RequestParam("id") String id,
                              @RequestParam("nombre") String empresa,
                              @RequestParam("cobertura") String cobertura,
                              @RequestParam("tarifa") String tarifa, Model model){
        Seguro se = new Seguro(Integer.parseInt(id), empresa, Double.parseDouble(cobertura), Double.parseDouble(tarifa));
        lista_seguro.add(se);
        model.addAttribute("lista", lista_seguro);
        return "listaSeguros";
    }

    @GetMapping(value = "/eliminarAuto/{id}")
    public String borrarAuto(@PathVariable("id") int id, Model model) {
        lista_auto.removeIf(auto -> auto.getId() == id);
        model.addAttribute("lista", lista_auto);
        return "listaAutos";  // Retorna la vista "detalleAuto.html"
    }

    @GetMapping(value = "/eliminarSeguro/{id}")
    public String borrarSeguro(@PathVariable("id") int id, Model model) {
        lista_seguro.removeIf(seguro -> seguro.getId() == id);
        model.addAttribute("lista", lista_seguro);
        return "listaSeguros";  // Retorna la vista "detalleAuto.html"
    }

    @GetMapping(value = "/eliminarSede/{id}")
    public String borrarSede(@PathVariable("id") int id, Model model) {
        lista_sede.removeIf(sede -> sede.getId() == id);
        model.addAttribute("lista", lista_sede);
        return "listaSedes";  // Retorna la vista "detalleAuto.html"
    }

    @GetMapping(value = "/mostrarEditarSede/{id}")
    public String mostrarEditarSede(@PathVariable("id") int id, Model model){
        Sede s = new Sede();
        for(Sede sede: lista_sede){
            if(sede.getId() == id) s = sede;
        }
        model.addAttribute("sede", s);
        return "editarSede";
    }

    @PostMapping(value = "/editarSede")
    public String EditarSede(@RequestParam("id") String id,
                             @RequestParam("direccion") String direccion,
                             @RequestParam("distrito") String distrito, Model model) {
        Integer ga = Integer.parseInt(id);
        Sede s = new Sede();
        for(Sede sede: lista_sede){
            if(sede.getId() == ga) s = sede;
        }

        s.setDireccion(direccion);
        s.setDistrito(distrito);
        model.addAttribute("lista", lista_sede);
        return "listaSedes";

    }

    @GetMapping(value = "/mostrarEditarSeguro/{id}")
    public String mostrarEditarSeguro(@PathVariable("id") int id, Model model){
        Seguro s = new Seguro();
        for(Seguro seguro: lista_seguro){
            if(seguro.getId() == id) s = seguro;
        }
        model.addAttribute("seguro", s);
        return "editarSeguro";
    }

    @PostMapping(value = "/editarSeguro")
    public String EditarSeguro(@RequestParam("id") String id,
                             @RequestParam("nombre") String nombre,
                             @RequestParam("cobertura") String cobertura,
                               @RequestParam("tarifa") String tarifa,  Model model) {
        Integer ga = Integer.parseInt(id);
        Seguro s = new Seguro();
        for(Seguro seguro: lista_seguro){
            if(seguro.getId() == ga) s = seguro;
        }

        s.setNombre(nombre);
        s.setCobertura(Double.parseDouble(cobertura));
        s.setTarifa(Double.parseDouble(tarifa));
        model.addAttribute("lista", lista_seguro);
        return "listaSeguros";

    }

    @GetMapping(value = "/mostrarEditarAuto/{id}")
    public String mostrarEditarAuto(@PathVariable("id") int id, Model model){
        Auto s = new Auto();
        for(Auto auto: lista_auto){
            if(auto.getId() == id) s = auto;
        }
        model.addAttribute("auto", s);
        return "editarAuto";
    }

    @PostMapping(value = "/editarAuto")
    public String EditarSeguro(@RequestParam("id") String id,
                               @RequestParam("modelo") String modelo,
                               @RequestParam("color") String color,
                               @RequestParam("kilometraje") String kilometraje,
                               @RequestParam("sede") String sede,
                               @RequestParam("costo") String costo, Model model) {
        Integer ga = Integer.parseInt(id);
        Auto s = new Auto();
        for(Auto auto: lista_auto){
            if(auto.getId() == ga) s = auto;
        }

        s.setColor(color);
        s.setModelo(modelo);
        s.setKilometraje(Integer.parseInt(kilometraje));
        s.setId_sede(Integer.parseInt(sede));
        s.setCosto_dia(Double.parseDouble(costo));
        model.addAttribute("lista", lista_auto);
        return "listaAutos";

    }


}
