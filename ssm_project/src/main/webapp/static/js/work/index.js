let vm = new Vue({
    el:'#main-container',
    data:function(){
        return{
            pageInfo:{},
            params:{
                status:'',
                startDate:'',
                endDate:''
            },
            name:'全部',
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
            nodes:[]
        }


    },
    methods:{
        selectPage:function (pageNum,pageSize) {
            axios({
                url:`manager/admin/work/selectPage/${pageNum}/${pageSize}`,
                params:this.params//任然受到  base标签影响
            }).then(response =>{//将上下文中的this(vue对象)传递到方法中
                this.pageInfo = response.data.obj;
            })
        },
        toDetail:function(id){
            layer.obj=id;
            layer.open({
                type:2,
                title:false,
                area:['80%', '80%'],
                content: 'manager/admin/work/toDetail' //iframe的url
            })
        },
        initTree:function () {
            axios({
                url:'manager/office/select'
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态生成父节点 全部
                this.nodes[this.nodes.length] = {id:0,name:'全部'};

                //初始化树(dom节点对象,配置对象,节点数组)
                let zTreeObj = $.fn.zTree.init($('#pullDownTreeone'),this.setting,this.nodes);
                let nodes = zTreeObj.getNodes();//获取组装后的节点数组


            }).catch(error=>{
                layer.msg(error.message)
            })

        },
        //event:事件对象  treeId：树对象id   treeNode 被点击的节点对象
        onClick:function (event,treeId,treeNode) {
            this.name=treeNode.name;
            //params赋值
            if(treeNode.id!=0){
                this.params.oid=treeNode.id;
            }else{
                this.params.oid=null;
            }
            $("#open-div").removeClass('open');//点击节点则移除  open样式  隐藏div

        },
        /**
         * 1.根据传入值，查找到所有模糊匹配的节点
         * 2.高亮显示查找到的节点
         */
        search:function () {
            //1.根据传入值，查找到所有模糊匹配的节点
            let zTreeObj = $.fn.zTree.getZTreeObj('pullDownTreeone');//根据树id获取树对象
            //初始化节点数据
            //获取所有节点  设置高亮属性为false
            let nodes = zTreeObj.getNodes();//得到多维结构的节点数组
            nodes = zTreeObj.transformToArray(nodes)//转换成一维结构 避免递归遍历
            for (let i in nodes){
                nodes[i].highLight=false;
                zTreeObj.updateNode(nodes[i]);
            }

            //getNodesByParamFuzzy(节点属性名,节点属性值,父节点):
            //根据指定父节点查找指定节点属性名上的模糊匹配结果，不指定父节点则查找整个树对象
            let nodesFuzzy = zTreeObj.getNodesByParamFuzzy('name',this.name,null);
            //2.高亮显示查找到的节点
            for (let i in nodesFuzzy) {
                nodesFuzzy[i].highLight=true;//将查找到的节点设置高亮属性
                zTreeObj.updateNode(nodesFuzzy[i]);//更新节点，触发fontCss设置
            }
        },
        fontCss:function (treeId,treeNode) {
            //如果节点有高亮属性为true则显示红色
            return treeNode.highLight?{color:"#ff0011"}:{color:"black"};
        },
        selectAll:function(){
            this.params=  {
                status:'', //初始化 让默认选中第一个
                startDate:'',
                endDate:''
            };
            this.name='';
            this.selectPage(1,this.pageInfo.pageSize);
        }
    },
    created:function () {
        this.selectPage(1,5);
        this.initTree();
    }
});