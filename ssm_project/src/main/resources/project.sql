#1.动态条件查询资质信息
#a.关联查询出所有资质信息
select * from qualification qu
LEFT JOIN
sys_user uu
on
qu.upload_user_id=uu.id   #uu 上传用户
LEFT JOIN
sys_user cu    						#    cu审核用户
ON
qu.check_user_id=cu.id 


#b.根据条件关联查询
select * from qualification qu
LEFT JOIN
sys_user uu
on
qu.upload_user_id=uu.id   #uu 上传用户
LEFT JOIN
sys_user cu    						#    cu审核用户
ON
qu.check_user_id=cu.id
where
qu.type=1
and
qu.`check`=0
and
qu.create_date >= '2019-01-01'
and
qu.create_date <= '2019-12-31'

#c.根据页面显示内容，选择投影列
SELECT
	qu.*,
	uu.NAME upload_name,
	cu.NAME check_name 
FROM
	qualification qu
	LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id
	LEFT JOIN sys_user cu ON qu.check_user_id = cu.id 
WHERE
	qu.del_flag = '0' 
	AND qu.type = '1' 
	AND qu.`check` = '0' 
	AND qu.create_date >= '2019-01-01' 
	AND qu.create_date <= '2019-12-31'


#日期分析   mysql日期处理函数   DATE    
#mysql日期比较： 两边转成同一格式进行比较  如果无法自动转换则失败
select DATE(create_date) from qualification  


#2.根据动态条件查询考核信息
#a.查询所有信息
select ex.*,su.name user_name,so.name office_name from examine ex , sys_user su ,sys_office so
where
ex.del_flag=0
and
ex.examine_user_id=su.id
and
su.office_id=so.id

#b.带条件查询
SELECT
	ex.*,
	su.NAME user_name,
	so.NAME office_name 
FROM
	examine ex,
	sys_user su,
	sys_office so 
WHERE
	ex.del_flag = 0 
	AND ex.type = 1 
	AND su.NAME LIKE concat( '%', '员', '%' ) 
	AND so.id = 56 
	AND ex.examine_user_id = su.id 
	AND su.office_id = so.id

#3.根据动态条件查询电子台账信息
#a.查询所有电子台账 
SELECT
wo.*,cu.name create_user,tu.name transport_user,ru.name recipient_user,co.name create_office
FROM
work_order AS wo
LEFT JOIN sys_user AS cu ON wo.create_user_id = cu.id
LEFT JOIN sys_user AS tu ON wo.transport_user_id = tu.id
LEFT JOIN sys_user AS ru ON wo.recipient_user_id = ru.id
LEFT JOIN sys_office AS co ON cu.office_id = co.id
LEFT JOIN sys_office AS `to` ON tu.office_id = `to`.id
LEFT JOIN sys_office AS ro ON ru.office_id = ro.id


#b.查询时间、状态和  包含  产废公司或运输公司或处废公司
SELECT
	wo.*,
	cu.NAME create_user,
	tu.NAME transport_user,
	ru.NAME recipient_user,
	co.NAME create_office 
FROM
	work_order AS wo
	LEFT JOIN sys_user AS cu ON wo.create_user_id = cu.id
	LEFT JOIN sys_user AS tu ON wo.transport_user_id = tu.id
	LEFT JOIN sys_user AS ru ON wo.recipient_user_id = ru.id
	LEFT JOIN sys_office AS co ON cu.office_id = co.id
	LEFT JOIN sys_office AS `to` ON tu.office_id = `to`.id
	LEFT JOIN sys_office AS ro ON ru.office_id = ro.id 
WHERE
	wo.del_flag = 0 
	AND wo.STATUS = '2' 
	AND wo.create_date >= '2016-09-01' 
	AND wo.create_date <= '2016-09-30' 
	AND ( co.id = 56 OR TO.id = 56 OR ro.id = 56 )


#4.查询电子台账详单
#a.查询指定电子台账
SELECT
	wo.CODE,
	wo.`status`,
	cu.NAME create_user_name,
	cu.phone create_user_phone,
	tu.NAME transport_user_name,
	tu.phone transport_user_phone,
	ru.NAME recipient_user_name,
	ru.phone recipient_user_phone,
	co.NAME create_office_name,
	`to`.NAME transport_office_name,
	ro.NAME recipient_office_name 
FROM
	work_order AS wo
	LEFT JOIN sys_user AS cu ON wo.create_user_id = cu.id
	LEFT JOIN sys_user AS tu ON wo.transport_user_id = tu.id
	LEFT JOIN sys_user AS ru ON wo.recipient_user_id = ru.id
	LEFT JOIN sys_office AS co ON cu.office_id = co.id
	LEFT JOIN sys_office AS `to` ON tu.office_id = `to`.id
	LEFT JOIN sys_office AS ro ON ru.office_id = ro.id 
WHERE
	wo.del_flag = 0 
	AND wo.id = 11
	
	
#b.根据工单查询 转运详单
SELECT
	de.*,
	wt.NAME waste_type_name,
	wt.`code` waste_type_code,
	wa.CODE waste_code 
FROM
	detail de,
	waste_type wt,
	waste wa 
WHERE
	de.work_order_id = 11 
	AND de.del_flag = 0 
	AND de.waste_type_id = wt.id 
	AND de.waste_id = wa.id
	
#c.根据工单查询 转运记录 根据创建日期倒序排序SELECT
	tr.*,
	su.NAME transfer_user_name,
	su.phone transfer_user_phone 
FROM
	transfer tr,
	sys_user su 
WHERE
	tr.work_order_id = 11 
	AND tr.del_flag = 0 
	AND tr.oprate_user_id = su.id 
ORDER BY
	tr.create_date DESC



