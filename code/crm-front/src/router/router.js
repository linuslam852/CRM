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
            ]
        },
    ]
})

export default router;