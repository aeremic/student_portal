var id;
jQuery(function () {
    // Brisanje korisnika
    // Pri kliku na Izbrisi uzmi id iz data-id
    $(".obrisiKorisnika").click(function () {
        id = $(this).attr('data-id');
    });

    // Prikazi id u modalu
    $('#obrisiKorisnikaModal').on('show.bs.modal', function (e) {
        $(this).find('.idkorisnika').text(id);
    });

    // Pri kliku na Potvrdi (Modal) obrisi korisnika
    $('#obrisiPotvrdiKorisnik').click(function (e) {
        // Kod za brisanje korisnika
        // alert(id + "obrisan");
        str = "admin/obrisiKorisnika/?id=" + id;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({id: id}, '', str);
                location.replace("http://localhost:8080/admin")
            }
        });
    });

    // Izmena korisnika
    // Pri kliku na Izmeni uzmi id iz data-id
    $(".editKorisnika").click(function () {
        id = $(this).attr('data-id');
    });

    // Prikazi id u modalu
    $('#editKorisnikaModal').on('show.bs.modal', function (e) {
        $(this).find('.idkorisnika').text(id);
    });

    // Pri kliku na Potvrdi (Modal) izmeni korisnika
    $('#editPotvrdiKorisnik').click(function (e) {
        // Kod za editovanje korisnika
        var ime = $("#korisnikImeIzmena").val();
        var prezime = $("#korisnikPrezimeIzmena").val();
        var uloga = $("#tipIzmena").find(":selected").text();
        // alert(ime + " " + uloga);
        str = "admin/editKorisnika/?id=" + id + "&imeKorisnika=" + ime + "&prezime=" + prezime + "&uloga=" + uloga;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({id: id}, '', str);
                location.replace("http://localhost:8080/admin")
            }
        });
    });

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
        // Kod za brisanje korisnika
        // alert(id + "obrisan");
        str = "admin/obrisiPredmet/?id=" + id;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({id: id}, '', str);
                location.replace("http://localhost:8080/admin")
            }
        });
    });

    // Izmena predmeta
    // Pri kliku na Izmeni uzmi id iz data-id
    $(".editPredmet").click(function () {
        id = $(this).attr('data-id');
    });

    // Prikazi id u modalu
    $('#editPredmetModal').on('show.bs.modal', function (e) {
        $(this).find('.idpredmeta').text(id);
    });

    // Pri kliku na Potvrdi (Modal) izmeni predmet
    $('#editPotvrdiPredmet').click(function (e) {
        // Kod za editovanje predmeta
        var nazivPredmeta = $("#predmetImeIzmena").val();
        var punoImeNastavnika = $(".nastavnikIzmena").find(":selected").text();
        //alert(nazivPredmeta + " " + punoImeNastavnika);
        var temp = punoImeNastavnika.split(" ");

        str = "admin/editPredmet/?id=" + id + "&nazivPredmeta=" + nazivPredmeta + "&imeNastavnika=" + punoImeNastavnika;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({}, '', str);
                location.replace("http://localhost:8080/admin")
            }
        });
    });

    // Dodaj korisnika
    $('#dodajKorisnika').click(function (e) {
        // Kod za dodavanje korisnika
        var imeKorisnika = $("#korisnikIme").val();
        var prezimeKorisnika = $("#korisnikPrezime").val();
        var uloga = $("#tip").find(":selected").text();
        // alert(imeKorisnika + " " + uloga);
        str = "admin/dodajKorisnika/?&imeKorisnika=" + imeKorisnika + "&prezime=" + prezimeKorisnika + "&uloga=" + uloga;
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({}, '', str);
                location.replace("http://localhost:8080/admin")
            }
        });
    });

    // Dodaj predmet
    $('#dodajPredmet').click(function (e) {
        // Kod za dodavanje korisnika
        var nazivPredmeta = $("#nazivPredmeta").val();
        var punoImeNastavnika = $(".nastavnik").find(":selected").text();
        //alert(nazivPredmeta + " " + punoImeNastavnika);
        var temp = punoImeNastavnika.split(" ");

        str = "admin/dodajPredmet/?&nazivPredmeta=" + nazivPredmeta + "&imeNastavnika=" + temp[0] + "&prezimeNastavnika=" + temp[1];
        $.ajax({
            url: str,
            type: 'GET',
            cache: false,
            success: function () {
                history.pushState({}, '', str);
                location.replace("http://localhost:8080/admin")
            }
        });
    });
});