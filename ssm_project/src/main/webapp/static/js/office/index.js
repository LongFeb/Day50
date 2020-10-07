let vm = new Vue({
    el:'#main-container',  //替换管理范围
    data:function(){
        return{
            pageInfo:{},
            params:{
                id:'',
                name:''
            },
            office:{},
            setting:{
                data: {
                    simpleData:{
                        enable:true,
                        pIdKey:'parentId',
                    }
                },
                edit:{
                    enable:true,
                    removeTitle: "删除",
                    renameTitle: "编辑",
                },
                callback:{
                    onClick:this.onClick,
                    beforeEditName: this.beforeEditName,
                    beforeRemove: this.beforeRemove,
                },
                view:{
                    addHoverDom:this.addHoverDom,
                    removeHoverDom: this.removeHoverDom,
                }
            },
            nodes:[]
        }
    },
    methods:{
        selectPage:function (pageNum,pageSize) {
            axios({
                url:`manager/office/selectPage/${pageNum}/${pageSize}`,  //任然受到  base标签影响
                params:this.params
            }).then(response =>{//将上下文中的this(vue对象)传递到方法中
                this.pageInfo = response.data.obj;
            }).catch(error=>{
                layer.msg(error.message);
            })
        },
        selectName:function(){
            this.params.id='';
            this.selectPage(1,this.pageInfo.pageSize)
        },
        selectAll:function(){
            this.params.id='';
            this.params.name='';
            this.selectPage(1,this.pageInfo.pageSize)
        },
        toUpdate:function (id) {
            layer.obj = id;//绑定id信息到index窗口对象中的layer对象里面
            layer.message='';
            /**
             * 1.通过layer弹出子窗口  需要发送请求到后台获取加载页面
             * 2.传递id到子窗口中，子窗口中发送请求查询id对应的对象
             */
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/office/toUpdate', //iframe的url
                end: () => {
                    if(layer.message!=''){
                        layer.msg("更新成功,更新记录数："+layer.message);
                        this.selectPage(1,5);
                    }
                }
            });
        },
        doDelete:function (area) {
            layer.msg('是否删除area',{
                time:20000,
                btn: ['确定','我再想想'],
                yes:()=>{
                    office.delFlag="1";//修改删除态
                    axios({
                        url:`manager/office/doUpdate`,
                        method: 'put',
                        data:area
                    }).then(response=>{
                        layer.msg(response.data.msg);
                        if(response.data.success){
                            this.selectPage(this.pageInfo.pageNum,this.pageInfo.pageSize);
                        }
                    })
                }
            })
        },
        toDetail:function (id) {
            layer.obj=id;//绑定id信息到index窗口对象中的layer对象里面
            layer.message='';
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/office/toDetail', //iframe的url
                end: () => {
                    if(layer.message!=''){
                        this.selectPage(1,5);
                    }
                }
            });
        },
        initTree:function () {
            axios({
                url:'manager/office/select'
            }).then(response=>{
                this.nodes=response.data.obj;
                //动态生成父节点 全部
                this.nodes[this.nodes.length] = {id:0,name:'全部公司'};
                //初始化树(dom节点对象,配置对象,节点数组)
                let zTreeObj = $.fn.zTree.init($('#treeMenu'),this.setting,this.nodes);
                let nodes = zTreeObj.getNodes();//获取组装后的节点数组

            }).catch(error=>{
                layer.msg(error.message)
            })

        },
        onClick:function (event,treeId,treeNode) {
            this.params.name='';
            //params赋值
            if(treeNode.id!=0){
                this.params.id=treeNode.id;
            }else{
                this.params.oid=null;
            }
            this.selectPage(1,this.pageInfo.pageSize);

        },
        beforeEditName:function (treeId,treeNode) {
            if(treeNode.id!=0){
                this.toUpdate(treeNode.id);
            }
            return false;
        },
        beforeRemove:function (treeId,treeNode) {
            if(treeNode.id!=0){
                area.id=treeNode.id;
                this.toDelete(area);
            }
            return false;
        },
        addHoverDom:function (treeId,treeNode) {
            // console.log("添加按钮");
            // console.log(treeNode)
            //当鼠标移到节点上触发，判断是否存在有动态添加的dom节点，没有才生成并添加dom

            //判断是否存在id为treeDemo_2_add的对象
            let span = $(`#${treeNode.tId}_add`);
            if(span.length>0){
                // console.log(span);
                return;
            }

            span = `<span class="button add" id="${treeNode.tId}_add" title="添加" treenode_add="" style=""></span>`
            $(`#${treeNode.tId}_a`).append(span);
            //绑定点击事件

            $(`#${treeNode.tId}_add`).on('click',function () {
                console.log(span);
            })
        },
        removeHoverDom: function (treeId,treeNode) {
            // console.log("移除按钮");
            // console.log(treeNode);
            //当鼠标离开节点触发，移除节点，解除事件对象绑定
            //off移除所有on绑定的事件
            $(`#${treeNode.tId}_add`).off().remove();
        },
    },
    created:function () {
        this.selectPage(1,5);
        this.initTree()
    }
});
