var urlData = "/admin/users/";

//добавить переходы на таблицы с диалоговых окон и таб

window.onload=function(){
    $("#addForm").submit( addUserButton);
    $("#addFormButton").click(function (){
        $("#firstTabLink").trigger("click");
    });
    $(".btn-primary").click(function (){
        $(".btn-secondary").trigger("click");
    });

    sendFetchRequest(urlData, undefined, "GET").then((responce)=>{
        return responce.json();
    }).then ((datas) => {
        for (let i = 0; i < datas.length; i++){
            let roleString;
            if(datas[i]['roles'].length == 2){
                roleString = "ADMIN, USER";
            } else if(datas[i]['roles'][0]['role'] == "ROLE_ADMIN"){
                roleString = "ADMIN";
            } else {
                roleString = "USER";
            }
            let tableStrings = "<tr id='"+ datas[i]['id'] +"'>"+
                "<td id='"+ datas[i]['id'] +"id'>" + datas[i]['id'] + "</td>"+
                "<td id='"+ datas[i]['id'] + "first_name'>" + datas[i]['firstName'] + "</td>"+
                "<td id='"+ datas[i]['id'] + "last_name'>" + datas[i]['lastName'] + "</td>"+
                "<td id='"+ datas[i]['id'] + "email'>" + datas[i]['email'] + "</td>"+
                "<td id='"+ datas[i]['id'] + "roles'>" + roleString + "</td>"+
                "<td id='"+ datas[i]['id'] + "age'>" + datas[i]['age'] + "</td>"+
                "<td>"+
                    "<button onClick='dataEditUser(this.id, \"UPDATE\")' id='" + datas[i]['id'] +"'"+
                            "class='btn btn-info' data-toggle='modal' data-target='#myModal'>"+
                        "Edit"+
                    "</button>"+
                "</td>"+
                "<td>" +
                    "<button onClick='dataEditUser(this.id, \"DELETE\")' id='" + datas[i]['id'] +"'"+
                            "class='btn btn-danger' data-toggle=\"modal\" data-target=\"#myModal\">"+
                        "Delete"+
                    "</button>"+
                "</td>"+
            "</tr>";
            $("tbody").append(tableStrings);
        }
    });
}

function dataEditUser(userId, actionOfUser){
    let arrayOfIds = ["id", "first_name", "last_name", "age", "email"];
    for (let i = 0; i < arrayOfIds.length; i++){
        addToEditData(userId + arrayOfIds[i], arrayOfIds[i]);
    }
    if(actionOfUser == "DELETE"){
        $("button.btn-primary").text("Delete");
        $("button.btn-primary").attr("onclick", "deleteUserButton(" + userId + ")");
        $("span.password").remove();

    } else {
        $("button.btn-primary").text("Edit");
        $("button.btn-primary").attr("onclick", "updateUserButton()")
        if($("span.password").length == 0){
            $("span.email").after("<span class=\"password\"><label>Password</label><br/><input type=\"password\""+
                "name=\"password\"/><br/>"+
                "</span>");
        }
    }
}

function addToEditData(readTextFromId, inputId){
    let valueName = $("#" + readTextFromId).text();
    document.querySelector("#updateForm input[name = '"+ inputId +"']").setAttribute("value", valueName);
}

async function sendFetchRequest(url, data, method){
    return await fetch(url,{
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}

function addUserButton(event){
    event.preventDefault();
    let userToAdd = {};
    userToAdd["firstName"] = $("#addForm input[name = 'first_name']").val();
    userToAdd["lastName"] = $("#addForm input[name = 'last_name']").val();
    userToAdd["age"] = $("#addForm input[name = 'age']").val();
    userToAdd["password"] = $("#addForm input[name = 'password']").val();
    userToAdd["roles"] = $("#addForm select[name = 'roles']").val();
    userToAdd["email"] = $("#addForm input[name = 'email']").val();
    sendFetchRequest(urlData, userToAdd, 'POST').then((response) => {
        return response.json();
    }).then((datas)=>{
        let roleString;

        if(datas['roles'].length == 2){
            roleString = "ADMIN, USER";
        } else if(datas['roles'][0]['role'] == "ROLE_ADMIN"){
            roleString = "ADMIN";
        } else {
            roleString = "USER";
        }
        let addTr = "<tr id = '" + datas['id'] + "'> " +
            "<td id='" + datas['id'] + "id'>" + datas['id'] + "</td>"+
            "<td id='" + datas['id'] + "first_name'>" + datas['firstName'] +"</td>"+
            "<td id='" + datas['id'] + "last_name'>" + datas['lastName'] +"</td>"+
            "<td id='" + datas['id'] + "email'>" + datas['email'] +"</td>"+
            "<td id='" + datas['id'] + "roles'>" + roleString +"</td>"+
            "<td id='" + datas['id'] + "age'>" + datas['age'] +"</td>"+
            "<td><button onClick='dataEditUser(this.id)' id='"+ datas['id'] +"'"+
        "class='btn btn-info' data-toggle='modal' data-target='#myModal'> Edit </button> </td>"+
        "<td> <button onclick='deleteUserButton(" + datas['id'] + ")' class='btn btn-danger'>"+
                "Delete </button> </td> </tr>"
        $("tbody").append(addTr);
        $("#addForm input[name = 'first_name']").val("First Name");
        $("#addForm input[name = 'last_name']").val("Last Name");
        $("#addForm input[name = 'age']").val("1");
        $("#addForm input[name = 'password']").val("");
        $("#addForm select[name = 'roles']").val("");
        $("#addForm input[name = 'email']").val("Email");
        alert("Пользователь добавлен");
    });
}


function deleteUserButton(userId){
    let deleteUrl = urlData + userId;
    sendFetchRequest(deleteUrl, undefined, 'DELETE').then((responce) => {
        if (responce.status == 200){
            $("tr#" + userId).remove();
            alert("Пользователь удален")
        }
    });
}

function updateUserButton(){
    let userToAdd = {};
    userToAdd["id"] = $("#updateForm input[name = 'id']").val();
    userToAdd["firstName"] = $("#updateForm input[name = 'first_name']").val();
    userToAdd["lastName"] = $("#updateForm input[name = 'last_name']").val();
    userToAdd["age"] = $("#updateForm input[name = 'age']").val();
    userToAdd["password"] = $("#updateForm input[name = 'password']").val();
    userToAdd["roles"] = $("#updateForm select[name = 'roles']").val();
    userToAdd["email"] = $("#updateForm input[name = 'email']").val();
    sendFetchRequest(urlData, userToAdd, 'PUT').then((response) => {
        return response.json();
    }).then((datas)=>{
        let roleString;
        if(datas['roles'].length == 2){
            roleString = "ADMIN, USER";
        } else if(datas['roles'][0]['role'] == "ROLE_ADMIN"){
            roleString = "ADMIN";
        } else {
            roleString = "USER";
        }

        $("td#" + datas['id'] + "first_name").text(datas['firstName']);
        $("td#" + datas['id'] + "last_name").text(datas['lastName']);
        $("td#" + datas['id'] + "age").text(datas['age']);
        $("td#" + datas['id'] + "roles").text(roleString);
        $("td#" + datas['id'] + "email").text(datas['email']);

        alert("Пользователь обновлен");

    });
}







