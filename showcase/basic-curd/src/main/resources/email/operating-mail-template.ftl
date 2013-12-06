<!DOCTYPE html>
<html>
<head>

    <title>异常错误报告</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
    
    <link rel="stylesheet" type="text/css" href="http://cdn.staticfile.org/twitter-bootstrap/3.0.3/css/bootstrap.min.css" />

    <!--[if IE 8]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script type="text/javascript" src="http://cdn.staticfile.org/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
</head>
<body>

	<div class="panel panel-default">
	
		<div class="panel-heading">
			<h3 class="panel-title"><span class="glyphicon glyphicon-link"></span> 操作记录清单</h3>
		</div>
		
		<div class="panel-body">
		
			<div class="panel panel-default">
				<table class="table table-striped">
		 			<thead>
			            <tr>
			                <th>操作用户</th>
							<th>ip地址</th>
							<th>操作目标</th>
							<th>执行方法</th>
							<th>模块名称</th>
							<th>功能名称</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>执行状态</th>
							
			            </tr>
		            </thead>
		            <tbody>
		            	<tr>
							<td>${entity.username!""}</td>
							<td>${entity.ip!""}</td>
							<td>${entity.operatingTarget!""}</td>
							<td>${entity.method!""}</td>
							<td>${entity.module!""}</td>
							<td>${entity.function!""}</td>
							<td>${entity.startDate!""}</td>
							<td>${entity.endDate!""}</td>
							<td>${entity.stateName}</td>
						</tr>
		           </tbody>
		        </table>
        	</div>
        	
        	<div class="panel panel-default">
				<div class="panel-heading">
				<h3 class="panel-title"><span class="glyphicon glyphicon-screenshot"></span> 描述</h3>
				</div>
				<div class="panel-body">
					${entity.remark!''}
				</div>
        	</div>
		</div>
		
	</div>
	
</body>
</html>