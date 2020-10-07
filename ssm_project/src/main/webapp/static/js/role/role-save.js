let vm = new Vue({
    el:'#main-container',
    data:function(){
        return{
            setting:{
                data:{//设置树的数据的配置
                    simpleData: {//简单数据格式配置  一维结构节点数组 不设置默认是多维结构数组
                        enable:true,  //开启使用简单数据格式
                        pIdKey: 'parentId' //设置父节点id属性key   与数据库中的字段需要对应
                    }
                },
                check:{
                    enable: true,
                    checkboxType:{"Y":"ps","N":"ps"}
                }
            },
            nodes:[],
            treeObj:{},
            role:{
                resources:[],
                oldResources:[],
                offices:[],
                oldOffices:[],
            },
            officeTreeObj:'',
            officeNodes:[]
        }
    },
    methods:{
        selectOne:function(){
            //查询role信息
            axios({
                url:'manager/role/selectOne',
                params:{rid:this.role.id}
            }).then(response=>{
                this.role=response.data.obj;
                if(this.role.dataScope==9){
                    this.initOfficeTree();
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        initTree:function () {
            axios({
                url:'manager/menu/select'
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态生成父节点 全部
                this.nodes[this.nodes.length] = {id:0,name:'所有权限'};

                //初始化树(dom节点对象,配置对象,节点数组)
                this.treeObj = $.fn.zTree.init($('#select-treetreeSelectResEdit'),this.setting,this.nodes);
                //设置选中
                this.selectByRid();
            }).catch(error=>{
                layer.msg(error.message)
            });

        },
        selectByRid:function(){
            axios({
                url:'manager/menu/selectByRid',
                params: {
                    rid:this.role.id,
                }
            }).then(response=>{
                this.role.resources=response.data.obj;
                this.role.oldResources=this.role.resources;
                let _resources=this.treeObj.transformToArray(this.treeObj.getNodes());
                for(let i in _resources){
                    for(let j in this.role.resources){
                        if(this.role.resources[j].id===_resources[i].id){
                            _resources[i].checked=true;
                            this.treeObj.updateNode(_resources[i]);
                        }
                    }
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        initOfficeTree:function () {
            axios({
                url:'manager/office/select'
            }).then(response=>{
                this.officeNodes=response.data.obj;
                //动态生成父节点 全部
                this.officeNodes[this.nodes.length] = {id:0,name:'所有权限'};

                //初始化树(dom节点对象,配置对象,节点数组)
                let officeTreeObj = $.fn.zTree.init($('#select-treetreeSelectOfficeEdit'),this.setting,this.officeNodes);
                $("#treeSelectOfficeEdit").css("display","inline-block");
                this.selectByOfficesByRid();
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        changeDataScope:function(){
            if(this.role.dataScope==9){
                this.initOfficeTree();
            }else {
                $.fn.zTree.destroy("select-treetreeSelectOfficeEdit");
                $("treeSelectOfficeEdit").css("display","none")
            }
        },
        selectByOfficesByRid:function(){
            axios({
                url:'manager/office/selectByRid',
                params: {
                    rid:this.role.id,
                }
            }).then(response=>{
                this.role.offices=response.data.obj;
                this.role.oldOffices=this.role.offices;
                let _offices=this.officeTreeObj.transformToArray(this.officeTreeObj.getNodes());
                for(let i in _offices){
                    for(let j in this.role.offices){
                        if(this.role.offices[j].id==_offices[i].id){
                            _offices[i].checked=true;
                            this.officeTreeObj.updateNode(_offices[i]);
                        }
                    }
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        update:function(){
            //处理资源，
            // 获取资源树对象中被选中的数据
            let _resources=this.treeObj.getCheckedNodes(true);
            if(_resources!=undefined&&_resources.length>0){
                if(_resources[0].id==0){
                    _resources.splice(0,1);
                }
                this.role.resources=_resources; //有改动则赋值新数据
            }else {
                layer.msg("请正确选中资源");
                return;
            }
            //处理公司授权
            if(this.officeTreeObj!=''){
                let _offices=this.officeTreeObj.getCheckedNodes(true);
                if(_offices!=undefined&&_offices.length>0){
                    if(_offices[0].id==0){
                        _offices.splice(0,1);
                    }
                    this.role.offices=_offices; //有改动则赋值新数据
                }else {
                    layer.msg("请正确选中授权公司");
                    return;
                }
            }

            axios({
                url:'manager/role/update',
                method:'put',
                data:this.role,
            }).then(response=>{
                layer.msg(response.data.msg);
            }).catch(function (error){
                layer.msg(error.message);
            })

        },

    },
    mounted:function(){

    },
    created:function () {
        this.role.id=parent.layer.obj;
        this.initTree();
        this.selectOne();
    }
});