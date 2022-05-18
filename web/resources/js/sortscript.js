$(document).ready(function () {

    $("select").selectBox();

    var $gallery = $(".gallery"),
            $trash = $(".tasks_container");
//            $trash = $("#trash");
    // Let the gallery items be draggable
    $("li", $gallery).draggable({
//                    start: function (event, ui) {
//                        $(this).css("z-index", 5);
//                    },
//                    cancel: "a", // clicking an icon won't initiate dragging
        revert: "invalid", // when not dropped, the item will revert back to its initial position
        containment: "#workplace",
//                helper: "clone",
        cursor: "move"
    });

    $trash.droppable({
        accept: ".gallery > li",
        drop: function (event, ui) {
            deleteImage(ui.draggable);
        }
    });

//                var recycle_icon = "<a href='link/to/recycle/script/when/we/have/js/off' title='Recycle this image' class='ui-icon ui-icon-refresh'>Recycle image</a>";
    function deleteImage($item) {
        $item.fadeOut(function () {
            var $list = $("ul", $trash).length ?
                    $("ul", $trash) :
                    $("<ul class='gallery'/>").appendTo($trash);

//                        $item.find("a.ui-icon-trash").remove();
            $item.appendTo($list);
        });
    }

    $('#sortable').sortable({
        stop: function () {
            var data = $("#sortable").sortable('serialize');
//                        $('h5').text(data);
            $.ajax({
                data: data,
                type: 'POST',
                url: '<%= request.getContextPath()%>/AjaxDev'
            });
        }
    });


});