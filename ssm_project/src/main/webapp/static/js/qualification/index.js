let vm = new Vue({
    el:'#main-container',  //替换管理范围
    data:{
        pageInfo:{},
        condition:{
            type:'',  //初始化 让默认选中第一个
            check:''
        }//参数对象
    },
    methods:{
        selectPage:function (pageNum,pageSize) {
            axios({
                url:`manager/qualification/selectPage/${pageNum}/${pageSize}`,  //任然受到  base标签影响
                params:this.condition
            }).then(response =>{//将上下文中的this(vue对象)传递到方法中
                this.pageInfo = response.data.obj;
            }).catch(error=>{
                layer.msg(error.message);
            })
        },
        selectAll:function(){
            this.condition=  {
                type:'',  //初始化 让默认选中第一个
                check:''
            };
            this.selectPage(1,this.pageInfo.pageSize);
        },
        toUpdate:function (id) {
            layer.obj = id;//绑定id信息到index窗口对象中的layer对象里面
            // layer.message='';
            /**
             * 1.通过layer弹出子窗口  需要发送请求到后台获取加载页面
             * 2.传递id到子窗口中，子窗口中发送请求查询id对应的对象
             */
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/qualification/toUpdate', //iframe的url
                end: () => {
                    if(layer.message!=''){
                        layer.msg("更新成功,更新记录数："+layer.message);
                        this.selectPage(1,5);
                    }
                }
            });
        }
    },
    created:function () {
        this.selectPage(1,5);
    }
})