var id;
jQuery(function () {
    // Brisanje predmeta
    // Pri kliku na Izbrisi uzmi id iz data-id
    $(".obrisiPredmet").click(function () {
        id = $(this).attr('data-id');
    });

    // Prikazi id u modalu
    $('#obrisiPredmetModal').on('show.bs.modal', function (e) {
        $(this).find('.idpredmeta').text(id);
    });

    // Pri kliku na Potvrdi (Modal) obrisi predmet
    $('#obrisiPotvrdiPredmet').click(function (e) {
        // Kod za brisanje predmeta


        var origin = window.location.origin; // Returns base URL (http://localhost:8080)
        var path = window.location.pathname; // Returns path only (/student4)

        str = path + "/obrisiPredmetStudent/?idPredmeta=" + id;
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

    $(".prijaviPredmet").click(function () {
        id = $(this).attr('data-id');
    });

    // Prikazi id u modalu
    $('#prijaviPredmetModal').on('show.bs.modal', function (e) {
        $(this).find('.idpredmeta').text(id);
    });

    // Prijavi predmet
    $('#prijaviPotvrdiPredmet').click(function (e) {
        // Kod za prijavljivanje predmeta

       // var nazivIspita = $("#predmetImeIzmena").val();

        var origin = window.location.origin; // Returns base URL (http://localhost:8080)
        var path = window.location.pathname; // Returns path only (/student4)

        str = path + "/prijaviIspitStudent/?idPredmeta=" + id;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({id: id}, '', str);
                location.replace(origin + path);
            },
            error: function () {
                alert("Prijava vec postoji");
                location.replace(origin + path);
            }
        });
    });

    // Izaberi predmet
    $('#izaberiPredmet').click(function (e) {
        // Kod za dodavanje korisnika
        var nazivPredmeta = $(".predmet").find(":selected").text();

        var origin = window.location.origin; // Returns base URL (http://localhost:8080)
        var path = window.location.pathname; // Returns path only (/student4)

        str = path + "/izaberiPredmetStudent/?nazivPredmeta=" + nazivPredmeta;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({}, '', str);
                location.replace(origin + path);
            },
            error: function () {
                alert("Predmet vec postoji");
                location.replace(origin + path);
            }
        });
    });
});