let vm = new Vue({
    el:'.page-content',
    data:function(){
        return{
            nodes:[],
            setting:{
                data:{//设置树的数据的配置
                    simpleData: {//简单数据格式配置  一维结构节点数组 不设置默认是多维结构数组
                        enable:true,  //开启使用简单数据格式
                        // idKey: 'id', //设置节点中的id属性key  默认
                        pIdKey: 'parentId' //设置父节点id属性key   与数据库中的字段需要对应
                    }
                },
                callback:{
                    onClick:this.onClick
                },
                view:{//显示设置
                    //当显示某个节点/更新节点的时候，自动会调用字体设置
                    fontCss: this.fontCss
                }
            },
            role:{}, //角色
            yxUsers:[], //已选人员列表
            removeBtn:false,  //移除已选人员的按钮
            yxIds:[],
            dxUsers:[],  //待选人员
            addBtn:false,  //添加待选人员的按钮
            dxIds:[],
            oid:''
        }
    },
    methods:{
        initTree:function () {
            axios({
                url:'manager/office/select'
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态生成父节点 全部
                //this.nodes[this.nodes.length] = {id:0,name:'全部机构'};

                //初始化树(dom节点对象,配置对象,节点数组)
                let zTreeObj = $.fn.zTree.init($('#treeOffice'),this.setting,this.nodes);

            }).catch(error=>{
                layer.msg(error.message)
            });

        },
        selectRole:function(id){
            axios({
                url: 'manager/sysuser/selectByRid',
                params:{rid:id}
            }).then(response=>{
                this.yxUsers=response.data.obj;
                for(let i in this.yxUsers){
                    this.yxUsers[i].checked=false;
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        checkYxUsers:function(id){

            for(let i in this.yxUsers){
                if(this.yxUsers[i].id===id){
                    this.yxUsers[i].checked=!this.yxUsers[i].checked;
                    if(this.yxUsers[i].checked){
                        this.yxIds.push(id);
                    }else {
                        for(let j=0; j<this.yxIds.length;j++){
                            if(this.yxIds[j]===id){
                                this.yxIds.splice(j,1); //移除数组中的元素splice(start,count)
                            }
                        }
                    }
                }
            }
            //判断是否显示（移除人员）按钮
            if(this.yxIds.length>0){
                this.removeBtn=true;
            }else {
                this.removeBtn=false;
            }
        },
        deleteBatch:function(){
            axios({
                url: 'manager/role/deleteBatch',
                params:{
                    rid:this.role.id,
                    //this.yxIds+'':将数组处理成字符串数组，
                    // 在转换json串过程中js会自动处理成key=val1,val2,val3的格式,
                    // ，该格式等同于key=val1&key=val2&...  后端可以自动处理成数组格式
                    ids:this.yxIds+'',
                }
            }).then(response=>{
                layer.msg(response.data.message);
                if(response.data.success){
                    this.initData();
                    this.selectRole(this.role.id);
                    if(this.oid!=''){
                        this.selectNoRole();
                    }
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        selectNoRole:function(){
            axios({
                url: 'manager/sysuser/selectNoRole',
                params:{rid:this.role.id,oid:this.oid}
            }).then(response=>{
                this.dxUsers=response.data.obj;
                for(let i in this.dxUsers){
                    this.dxUsers[i].checked=false;
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        checkDxUsers:function(id){
            for(let i in this.dxUsers){
                if(this.dxUsers[i].id===id){
                    this.dxUsers[i].checked=!this.dxUsers[i].checked;
                    if(this.dxUsers[i].checked){
                        this.dxIds.push(id);
                    }else {
                        for(let j=0; j<this.dxIds.length;j++){
                            if(this.dxIds[j]===id){
                                this.dxIds.splice(j,1); //移除数组中的元素splice(start,count)
                            }
                        }
                    }
                }
            }
            //判断是否显示（移除人员）按钮
            if(this.dxIds.length>0){
                this.addBtn=true;
            }else {
                this.addBtn=false;
            }
        },
        insertBatch:function(){
            axios({
                url: 'manager/role/insertBatch',
                params:{
                    rid:this.role.id,
                    //this.yxIds+'':将数组处理成字符串数组，
                    // 在转换json串过程中js会自动处理成key=val1,val2,val3的格式,
                    // ，该格式等同于key=val1&key=val2&...  后端可以自动处理成数组格式
                    cids:this.dxIds+'',
                }
            }).then(response=>{
                layer.msg(response.data.message);
                if(response.data.success){
                    this.initData();
                    this.selectNoRole();
                    this.selectRole(this.role.id);
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        initData:function(){
            this.yxUsers=[];
            this.removeBtn=false;
            this.yxIds=[];
            this.dxUsers=[];
            this.dxIds=[];
            this.addBtn=false;
        },

        //event:事件对象  treeId：树对象id   treeNode 被点击的节点对象
        onClick:function (event,treeId,treeNode) {
            let zTreeObj=$.fn.zTree.getZTreeObj("treeOffice");
            let nodes=zTreeObj.getNodes();
            nodes=zTreeObj.transformToArray(nodes);
            for(let i in nodes){
                nodes[i].highLight=false;
                zTreeObj.updateNode(nodes[i]);
            }
            treeNode.highLight=true;
            zTreeObj.updateNode(treeNode);
            this.oid=treeNode.id;
            this.selectNoRole();
        },
        fontCss:function (treeId,treeNode) {
            //如果节点有高亮属性为true则显示红色
            return treeNode.highLight?{color:"#ff0011"}:{color:"black"};
        }
    },
    mounted:function(){

    },
    created:function () {
        this.initTree();
        this.role.id=parent.layer.roleId;
        this.role.name=parent.layer.roleName;
        this.selectRole(this.role.id);

    }
});