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
        }
    ]
})

export default router;