$(document).ready(function () {

    $(".panel-heading").click(function () {
        var $panelBody = $(this).next(".panel-body");
        if ($panelBody.css("display") == "none"){
            $panelBody.fadeIn(200);
            $(this).children().children(".row-down").css("transform","rotate(180deg)");
           // $panelBody.css("display", "block");
        }else{
            $panelBody.fadeOut(200);
            $(this).children().children(".row-down").css("transform","rotate(0deg)");
           // $panelBody.css("display", "none");
        }
    });

});