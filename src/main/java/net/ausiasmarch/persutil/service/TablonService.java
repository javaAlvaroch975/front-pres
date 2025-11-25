package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.persutil.entity.TablonEntity;
import net.ausiasmarch.persutil.repository.TablonRepository;

@Service
public class TablonService {

    @Autowired
    TablonRepository oTablonRepository;

    @Autowired
    AleatorioService oAleatorioService;

    ArrayList<String> alAnuncios = new ArrayList<>();

    public TablonService() {
        alAnuncios.add("La vida es bella.");
        alAnuncios.add("El conocimiento es poder.");
        alAnuncios.add("La perseverancia es la clave del éxito.");
        alAnuncios.add("El tiempo es oro.");
        alAnuncios.add("La creatividad es la inteligencia divirtiéndose.");
        alAnuncios.add("Más vale tarde que nunca.");
        alAnuncios.add("El cambio es la única constante en la vida.");
        alAnuncios.add("La esperanza es lo último que se pierde.");
        alAnuncios.add("La unión hace la fuerza.");
        alAnuncios.add("El respeto es la base de toda relación.");
        alAnuncios.add("La comunicación es clave en cualquier relación.");
        alAnuncios.add("Más vale pájaro en mano que ciento volando.");
        alAnuncios.add("A mal tiempo, buena cara.");
        alAnuncios.add("El que no arriesga no gana.");
        alAnuncios.add("La suerte favorece a los audaces.");
        alAnuncios.add("El tiempo lo dirá.");
    }

    public Long rellenaTablon(Long numPosts) {
        for (long j = 0; j < numPosts; j++) {
            // crea entity tablon y la rellana con datos aleatorios
            TablonEntity oTablonEntity = new TablonEntity();
            oTablonEntity.setTitulo(alAnuncios.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, alAnuncios.size() - 1)));
            // rellena contenido
            String contenidoGenerado = "";
            int numFrases = oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 30);
            for (int i = 1; i <= numFrases; i++) {
                contenidoGenerado += alAnuncios.get(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, alAnuncios.size() - 1)) + " ";
                if (oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 10) == 1) {
                    contenidoGenerado += "\n";
                }
            }
            oTablonEntity.setContenido(contenidoGenerado.trim());
            contenidoGenerado += "\n";
            // extraer 5 palabras aleatorias del contenido  para las etiquetas
            String[] palabras = contenidoGenerado.split(" ");
            // eliminar signos de puntuacion de las palabras
            for (int i = 0; i < palabras.length; i++) {
                palabras[i] = palabras[i].replace(".", "").replace(",", "").replace(";", "").replace(":", "").replace("!", "").replace("?", "");
            }
            // convertir todas las palabras a minúsculas
            for (int i = 0; i < palabras.length; i++) {
                palabras[i] = palabras[i].toLowerCase();
            }
            // seleccionar palabras de más de 4 letras
            ArrayList<String> alPalabrasFiltradas = new ArrayList<>();
            for (String palabra : palabras) {
                if (palabra.length() > 4 && !alPalabrasFiltradas.contains(palabra)) {
                    alPalabrasFiltradas.add(palabra);
                }
            }
            palabras = alPalabrasFiltradas.toArray(new String[0]);
            String etiquetas = "";
            for (int i = 0; i < 5; i++) {
                String palabra = palabras[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, palabras.length - 1)];
                if (!etiquetas.contains(palabra)) {
                    etiquetas += palabra + ", ";
                }
            }
            // eliminar la última coma y espacio
            if (etiquetas.endsWith(", ")) {
                etiquetas = etiquetas.substring(0, etiquetas.length() - 2);
            }
            oTablonEntity.setEtiquetas(etiquetas);
            // establecer fecha de creación y modificación
            oTablonEntity.setFechaCreacion(LocalDateTime.now());
            oTablonEntity.setFechaModificacion(null);
            oTablonEntity.setPublico(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, 1) == 1);
            // guardar entity en base de datos
            oTablonRepository.save(oTablonEntity);
        }
        return oTablonRepository.count();
    }

    // ----------------------------CRUD---------------------------------
    public TablonEntity get(Long id) {
        return oTablonRepository.findById(id).orElseThrow(() -> new RuntimeException("Tablon not found"));
    }

    public Long create(TablonEntity tablonEntity) {
        tablonEntity.setFechaCreacion(LocalDateTime.now());
        tablonEntity.setFechaModificacion(null);
        oTablonRepository.save(tablonEntity);
        return tablonEntity.getId();
    }

    public Long update(TablonEntity tablonEntity) {
        TablonEntity existingTablon = oTablonRepository.findById(tablonEntity.getId())
                .orElseThrow(() -> new RuntimeException("Tablon not found"));
        existingTablon.setTitulo(tablonEntity.getTitulo());
        existingTablon.setContenido(tablonEntity.getContenido());
        existingTablon.setEtiquetas(tablonEntity.getEtiquetas());
        existingTablon.setFechaModificacion(LocalDateTime.now());
        oTablonRepository.save(existingTablon);
        return existingTablon.getId();
    }

    public Long delete(Long id) {
        oTablonRepository.deleteById(id);
        return id;
    }

    public Page<TablonEntity> getPage(Pageable oPageable) {
        return oTablonRepository.findAll(oPageable);
    }

    public Long count() {
        return oTablonRepository.count();
    }

}
