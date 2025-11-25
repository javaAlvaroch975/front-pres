package net.ausiasmarch.persutil.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.persutil.entity.TablonEntity;
import net.ausiasmarch.persutil.service.AleatorioService;
import net.ausiasmarch.persutil.service.TablonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tablon")
public class TablonApi {

    @Autowired
    AleatorioService oAleatorioService;

    @Autowired
    TablonService oTablonService;

    @GetMapping("/saludar")
    public ResponseEntity<String> saludar() {
        return new ResponseEntity<>("\"Hola desde el blog\"", HttpStatus.OK);
    }

    @GetMapping("/saludar/buenosdias")
    public ResponseEntity<String> saludarPorLaMañana() {
        return new ResponseEntity<>("\"Hola buenos dias desde el blog\"", HttpStatus.OK);
    }

    @GetMapping("/aleatorio") //endpoint
    public ResponseEntity<Integer> aleatorio() {
        int numeroAleatorio = (int) (Math.random() * 100) + 1;
        return ResponseEntity.ok(numeroAleatorio);
    }

    @GetMapping("/aleatorio/{min}/{max}") //endpoint
    public ResponseEntity<Integer> aleatorioEnRango(
            @PathVariable int min,
            @PathVariable int max) {
        int numeroAleatorio = (int) (Math.random() * (max - min + 1)) + min;
        return ResponseEntity.ok(numeroAleatorio);
    }

    @GetMapping("/aleatorio/service/{min}/{max}") //endpoint
    public ResponseEntity<Integer> aleatorioUsandoServiceEnRango(
            @PathVariable int min,
            @PathVariable int max) {
        return ResponseEntity.ok(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(min, max));
    }

    //---------------------------Rellenar datos fake blog---------------------------------

    @GetMapping("/rellena/{numPosts}")
    public ResponseEntity<Long> rellenaBlog(
            @PathVariable Long numPosts
    ) {
        return ResponseEntity.ok(oBlogService.rellenaBlog(numPosts));
    }



    
    // ----------------------------CRUD---------------------------------

    // obtener post por id
    @GetMapping("/{id}")
    public ResponseEntity<BlogEntity> get(@PathVariable Long id) {
        return ResponseEntity.ok(oBlogService.get(id));
    }

    // crear posts
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody BlogEntity blogEntity) {
        return ResponseEntity.ok(oBlogService.create(blogEntity));
    }

    // modificar posts
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody BlogEntity blogEntity) {
        return ResponseEntity.ok(oBlogService.update(blogEntity));
    }

    // borrar posts
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oBlogService.delete(id));
    }

    // listado paginado de posts
    @GetMapping("")
    public ResponseEntity<Page<BlogEntity>> getPage(Pageable oPageable) {
        return ResponseEntity.ok(oBlogService.getPage(oPageable));
        
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(oBlogService.count()); 
    }