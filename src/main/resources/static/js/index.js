let url = ""

function postUrl() {
    url = $('#text-input').val();

    $.ajax({
        type: "POST",
        url: "/short-links",
        contentType: "application/json",
        data: JSON.stringify(url),
    });
}

function createUrl() {
    if (url !== "") {
        $.ajax({
            type: "GET",
            url: "/u/" + url,
            success: function (response) {
                $("#shortUrl").replaceWith(`<p id="shortUrl">${response}</p>`)
            }
        });
    }
}