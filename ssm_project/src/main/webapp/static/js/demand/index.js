let vm = new Vue({
    el:'#main-container',
    data:{
        pageInfo:{}
    },
    methods:{
        selectPage:function (pageNum,pageSize) {
            axios({
                url:`manager/demand/selectPage/${pageNum}/${pageSize}`  //任然受到  base标签影响
            }).then(response =>{//将上下文中的this(vue对象)传递到方法中
                this.pageInfo = response.data.obj;
            })
        },
        toUpdate:function (id) {
            layer.obj=id;
            layer.message='';
            layer.open({
                type:2,
                title:false,
                area:['80%','80%'],
                content: 'manager/demand/toUpdate',
                end:()=>{
                    if(layer.message!=''){
                        layer.msg("更新成功,更新记录数："+layer.message);
                        this.selectPage(1,5);
                    }
                }
            });
        },
        toDetail:function (id) {
            layer.obj=id;//绑定id信息到index窗口对象中的layer对象里面
            layer.message='';
            layer.open({
                type: 2,    // 弹出类型  2为iframe  会在当前页面上动态生成一个dom，以iframe方式写入
                title: false,//子窗口标题    false则不显示
                area: ['80%', '80%'],        //窗口大小  [宽,高]   支持px和占据父窗口比例
                content: 'manager/demand/toDetail', //iframe的url
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