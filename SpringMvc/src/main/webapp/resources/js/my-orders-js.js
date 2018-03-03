$(document).ready(function () {

    $(".panel-heading").click(function () {
        var $panelBody = $(this).next(".panel-body");
        if ($panelBody.css("display") == "none"){
            $panelBody.fadeIn(200);
           // $panelBody.css("display", "block");
        }else{
            $panelBody.fadeOut(200);
           // $panelBody.css("display", "none");
        }
    });

});