let vm = new Vue({
    el:'#main-container',  //替换管理范围
    data:{
        pageInfo:{},
        type:'',//初始化 让默认选中第一个
        active:true,
        statute:{},
        ueditorConfig: {
            UEDITOR_HOME_URL:'static/ueditor/',
            serverUrl:'doUeditor',
            maximumWords: 200000
        }
    },
    methods:{
        selectPage:function (pageNum,pageSize) {
            axios({
                url:`manager/statute/selectPage/${pageNum}/${pageSize}`,  //任然受到  base标签影响
                params:{type:this.type}
            }).then(response =>{//将上下文中的this(vue对象)传递到方法中
                this.pageInfo = response.data.obj;
            })
        },
        selectAll:function(){
            this.type='';
            this.selectPage(1,this.pageInfo.pageSize);
        },
        changeActive:function () {
            this.active=!this.active;
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
                content: 'manager/statute/toUpdate', //iframe的url
                end: () => {
                    if(layer.message!=''){
                        layer.msg("更新成功,更新记录数："+layer.message);
                        this.selectPage(1,5);
                    }
                }
            });
        },
        insert:function () {
            axios({
                url: `manager/statute/insert`,
                method:'post',
                data: this.statute
            }).then(response=>{
                //1.插入成功  切换到列表   清空插入表单内容
                if(response.data.success){
                    this.active=!this.active;
                    this.statute={};
                }else {
                    layer.msg(response.data.msg);
                }
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        doDelete:function (statute) {
            layer.msg('是否删除statute',{
                time:20000,
                btn: ['确定','我再想想'],
                yes:()=>{
                    statute.delFlag="1";//修改删除态
                    axios({
                        url:`manager/statute/doUpdate`,
                        method: 'put',
                        data:statute
                    }).then(response=>{
                        layer.msg(response.data.msg);
                        if(response.data.success){
                            this.selectPage(this.pageInfo.pageNum,this.pageInfo.pageSize);
                        }
                    })
                }
            })
        }
    },
    created:function () {
        this.selectPage(1,5);
    },
    mounted:function () {
        jeDate({
            dateCell: '#indate',
            format: 'YYYY-MM-DD hh:mm:ss',
            zIndex: 999999999,   //图层,,,,优先级
            okfun:val => {//点击确定后的回调
                this.statute.createDate=val;
            },
            choosefun:val => {//选中日期后的回调
                this.statute.createDate=val;
            },
        });
    },
    components: {
        VueUeditorWrap
    }
});