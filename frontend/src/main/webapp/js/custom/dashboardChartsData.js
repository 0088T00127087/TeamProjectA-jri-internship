function getTopicLable(){
	$.post({
		url: "/frontend/DashboardChartDataServlet",
		cache: false,		
		type : "POST",
		data : {topic_id: "0"}
	},function(result){
		console.log(result);
	});
}