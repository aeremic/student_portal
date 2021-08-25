var id;
jQuery(function () {
    // Izmena ocene
    // Pri kliku na Izmeni uzmi id iz data-id
    $(".editOcena").click(function () {
        idStudenta = $(this).attr('data-id');
        idPredmeta = $(this).attr('data-idpredmeta');
    });

    // Prikazi id u modalu
    $('#editOcenaModal').on('show.bs.modal', function (e) {
        $(this).find('.idpredmeta').text(idStudenta);
        $(this).find('.idpredmeta').text(idPredmeta);
    });

    // Pri kliku na Potvrdi (Modal) izmeni ocenu
    $('#editPotvrdiOcena').click(function (e) {
        // Kod za editovanje ocene
        var ocena = $('.ocenaIzmena').find(":selected").text();
        //var idPredmeta = $('.ocenaIzmena').find(":selected").text();
        //id = -1; // temp

        var origin = window.location.origin; // Returns base URL (http://localhost:8080)
        var path = window.location.pathname; // Returns path only (/profesor4)

        str = path + "/izmeniOcenuProfesor/?idStudenta=" + idStudenta + "&idPredmeta=" + idPredmeta + "&ocena=" + ocena;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({id: id}, '', str);
                location.replace(origin + path);
            }
        });
    });
});