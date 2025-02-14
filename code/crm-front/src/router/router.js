import {createRouter, createWebHistory} from "vue-router";

let router = createRouter({
    history : createWebHistory(),
    routes :[
        {
            path: '/',
            component: () => import('../view/LoginView.vue'),
        },
        {
            path: '/dashboard',
            component: () => import('../view/DashBoardView.vue'),
            children:[
                {
                    path: '',
                    component: () => import('../view/StatisticView.vue'),
                },
                {
                    path: 'user',
                    component: () => import('../view/UserView.vue'),
                },
                {
                    //id是動態變量
                    path: 'user/:id',
                    component: () => import('../view/UserDetailView.vue'),
                },
                {

                    path: 'activity',
                    component: () => import('../view/ActivityView.vue'),
                },
                {
                    path:'activity/add',
                    component: () => import('../view/ActivityRecordView.vue'),
                },
                {
                    path:'activity/edit/:id',
                    component: () => import('../view/ActivityRecordView.vue'),
                },
                {
                    path:'activity/:id',
                    component: () => import('../view/ActivityDetailView.vue'),
                },
                {
                    path:'clue',
                    component: () => import('../view/ClueView.vue'),
                },
                {
                    path:'clue/add',
                    component: () => import('../view/ClueRecordView.vue'),
                },
                {
                    path:'clue/edit/:id',
                    component: () => import('../view/ClueRecordView.vue'),
                },
                {
                    path:'clue/detail/:id',
                    component: () => import('../view/ClueDetailView.vue'),
                },
                {
                    path:'customer',
                    component: () => import('../view/CustomerView.vue'),
                },
                {
                    path:'tran',
                    component: () => import('../view/TranView.vue'),
                },
                {
                    path:'customer/detail/:id',
                    component: () => import('../view/CustomerDetailView.vue'),
                },
                {
                    path:'tran/detail/:id',
                    component: () => import('../view/TranDetailView.vue'),
                },

            ]
        },
    ]
})

export default router;