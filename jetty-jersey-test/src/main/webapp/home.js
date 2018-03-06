
function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url
    }).done(success);
}

function callDone(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#result").append(html);
}

$(function(){
	$("#get").click(function(){
		getServerData("ws/example/get",callDone);
	});
	$("#post").click(function(){
		getServerData("ws/example/post",callDone);
	});
	$("#put").click(function(){
		getServerData("ws/example/put",callDone);
	});
	$("#delete").click(function(){
		getServerData("ws/example/delete",callDone);
	});
});