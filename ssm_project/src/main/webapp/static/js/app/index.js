let vm = new Vue({
    el:'#main-container',
    data:{
        pageInfo:{},
        active:true,
        app:{}
    },
    methods:{
        selectPage:function (pageNum,pageSize) {
            axios({
                url:`manager/app/selectPage/${pageNum}/${pageSize}`  //任然受到  base标签影响
            }).then(response =>{//将上下文中的this(vue对象)传递到方法中
                this.pageInfo = response.data.obj;
            })
        },
        toUpdate:function (id) {
            layer.obj=id;//绑定id信息到index窗口对象中的layer对象里面
            layer.message='';
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/app/toUpdate', //iframe的url
                end: () => {
                    if(layer.message!=''){
                        layer.msg("更新成功,更新记录数："+layer.message);
                        this.selectPage(1,5);
                    }
                }
            });
        },
        changeActive:function () {
            this.active=!this.active;
        },
        insert:function () {
            axios({
                url: `manager/app/insert`,
                method:'post',
                data: this.app
            }).then(response=>{
                //1.插入成功  切换到列表   清空插入表单内容
                if(response.data.success){
                    this.active=!this.active;
                    this.app={};
                }else {
                    layer.msg(response.data.msg);
                }
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        doDelete:function (app) {
            layer.msg('是否删除app',{
                time:20000,
                btn: ['确定','我再想想'],
                yes:()=>{
                    app.delFlag="1";//修改删除态
                    axios({
                        url:`manager/app/doUpdate`,
                        method: 'put',
                        data:app
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
                content: 'manager/app/toDetail', //iframe的url
                end: () => {
                    if(layer.message!=''){
                        this.selectPage(1,5);
                    }
                }
            });
        }
    },
    created:function () {
        this.selectPage(1,5);
    }
});