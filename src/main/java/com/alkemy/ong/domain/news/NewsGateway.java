package com.alkemy.ong.domain.news;

public interface NewsGateway {


    //POST /news - Deberá validar la existencia de los campos enviados,
    // para almacenar el registro en la tabla News. Antes de almacenarla,
    // deberá asignarle la columna type con el valor "news".


    //GET a /news/:id . Mostrará todos los campos de una entry
    // en base al id enviado por parámetro.
    //¿Pongo acá lo que implemento en el servicio?

    News findById(Long newsId);

}
