import axios from "axios";

axios.defaults.baseURL = "http://localhost:8088"
export function doGet(url, params){
    return axios({
        method: "get",
        url: url,
        params: params,
        dataType: "json"
    })
}

export function doPost(url, data){
    return axios({
        method: "post",
        url: url,
        data: data,
        dataType: "json"
    })
}

export function doPut(url, data){
    axios({
        method: "put",
        url: url,
        data: data,
        dataType: "json"
    }).then(function(rep){
        var s = "";
        rep.data.forEach(function (stu){
            s += stu.name + "-----------" + stu.age +"<br>";
        });
        document.getElementById("mydiv").innerHTML = s;
    })
}

export function doDelete(url, params){
    axios({
        method: "delete",
        url: url,
        params: params,
        dataType: "json"
    }).then(function(rep){
        var s = "";
        rep.data.forEach(function (stu){
            s += stu.name + "-----------" + stu.age +"<br>";
        });
        document.getElementById("mydiv").innerHTML = s;
    })
}