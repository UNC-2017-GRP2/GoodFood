
function submitJson(){
    var $form = $(".send-for-json-form");
    var objectTypeId = $form.attr("object-type-id");
    var entity = {};
    entity["objectId"] = null;
    entity["objectTypeId"] = objectTypeId;
    entity["name"] = "testlogin";
    var parameters = [];
    $form.find("input").not('[type="hidden"]').each(function() {
        var attrId = $(this).attr("attr-id");
        var value = $(this).val();
        var attribute = {};
        if (attrId != null ){
            attribute["attributeId"] = attrId;
            attribute["value"] = value;
            parameters.push(attribute);
        }
    });
    entity["parameters"] = parameters;
    $.ajax({
        url : 'sendEntity',
        type: 'GET',
        data : ({
            jsonEntity : JSON.stringify(entity)
        }),
        success: function (data) {
            alert(data.name);
        },
        error: function (data) {
            alert("error");
        }
    });

}