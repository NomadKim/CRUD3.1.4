function dataEditUser(userId){
    let name = userId + "name";
    let first = userId + "firstName";
    let second = userId + "lastname";
    let age = userId + "age";
    let email = userId + "email";
    $("input[name = first_name]").attr("value", $("td."+name).innerText);
}