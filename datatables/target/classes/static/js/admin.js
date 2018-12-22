$(function($) {
	var table = $("#paginatedTable").DataTable({
		"processing": true,
		"serverSide": true,
		"destroy": true,
		"lengthMenu": [[5,7,10], [5,7,10]],
		"displayLength": 5,
		"order": [[0, "asc"]],
		"StateSave": false,
		"searchable": true,
		"ajax": {
			"url": "/getData",
			"data": function(d) {
				d.extraMsg = "Hello Message";
			},
			"dataSrc": "userList"
		},
		"columns":[
			{
				"data": "0", "name": "User Id", "title": "User ID"
			},
			{
				"data": "1", "name": "First Name", "title": "First Name"
			},
			{
				"data": "2", "name": "Last Name", "title": "Last Name"
			}
		]
	});
});