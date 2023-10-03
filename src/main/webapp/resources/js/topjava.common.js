let form;

function makeEditable(datatableApi) {
    ctx.datatableApi = datatableApi;
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    console.log('topjava.common.js - add()');
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    $("#editRow").modal();
}

function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function myFormatDate(date) {
    return (
        [
            padTo2Digits(date.getDate()),
            padTo2Digits(date.getMonth() + 1),
            date.getFullYear(),
        ].join('.')
        + ' '
        + [
            padTo2Digits(date.getHours()),
            padTo2Digits(date.getMinutes())
        ].join(':')
    );
}

function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html(i18n["editTitle"]);
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (key === "dateTime") {
                var jsDate = myFormatDate(new Date(Date.parse(value)));
                console.log("jsDate = " + jsDate);
                form.find("input[name='" + key + "']").val(jsDate);
            } else {
                form.find("input[name='" + key + "']").val(value);
            }
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function save() {
    console.log('topjava.common.js - save()');

    // создание массива объектов из данных формы
    var serializedForm = form.serializeArray();

    // $.each(serializedForm, function(key, serializedForm)
    // переберём каждый элемент массива объектов
    $.each(serializedForm, function()
    {
        // выведем в консоль элемент в формате имя элемента=значение
        console.log(this.name + '=' + this.value);
        if (this.name === "dateTime") {
            let split = this.value.replace(" ", ".").split(".");
            this.value = [split[2], split[1], split[0]].join('-') + 'T' + split[3];
        }
        console.log(this.name + '=' + this.value);
    });

    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: serializedForm
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("common.saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + i18n["common.errorStatus"] + ": " + jqXHR.status + (jqXHR.responseJSON ? "<br>" + jqXHR.responseJSON : ""),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}