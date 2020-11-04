import VueRouter from 'vue-router'

import panel from './pages/panel'
import login from './pages/login'
import error404 from './pages/error404'
import inicio from './pages/inicio'
import operaciones from './pages/operaciones'
import egresos from './components/egresos'
import ingresos from './components/ingresos'

const routes = [
    {
        path: '/',
        component: panel,
        children: [
            {
                name: "index",
                path: '',
                component: inicio
            },
            {
                name: 'operaciones',
                path: 'operaciones',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                        label: "Operaciones",
                        type: "fixed",
                        path: "/operaciones",
                        active: true
                    }]
                },
                children: [
                    {
                        name: 'egresos',
                        path: 'egreso',
                        component: egresos,
                        meta: {
                            breadcrumb: [{
                                label: "Operaciones",
                                type: "fixed",
                                path: "/operaciones"
                            },
                            {
                                label: "Egresos",
                                type: "fixed",
                                path: "/operaciones/egreso",
                                active: true
                            }]
                        }
                    },
                    {
                        name: 'ingresos',
                        path: 'ingreso',
                        component: ingresos,
                        meta: {
                            breadcrumb: [{
                                label: "Operaciones",
                                type: "fixed",
                                path: "/operaciones"
                            },
                            {
                                label: "Ingresos",
                                type: "fixed",
                                path: "/operaciones/ingreso",
                                active: true
                            }]
                        }
                    }
                ]
            }
        ]
    },
    {
        path: '/login',
        component: login
    },
    {
        path: '/*',
        component: error404,
    }
];

export default new VueRouter({
    routes: routes,
    mode: 'history'
});

/*const routes = [
    {
        path: '/',
        component: panel,
        children: [
            {
                name: 'index',
                path: '',
                component: inicio
            },
            {
                name: 'operaciones',
                path: 'operaciones',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                        label: "Operaciones",
                        type: "fixed",
                        path: "/operaciones",
                        active: true
                    }]
                }
            },
            {
                name: 'ingresos',
                path: 'operaciones/ingreso',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                            label: "Operaciones",
                            type: "fixed",
                            path: "/operaciones"
                        },
                        {
                            label: "Ingresos",
                            type: "fixed",
                            path: "/operaciones/ingreso",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'agregarIngreso',
                path: 'operaciones/ingreso/agregar',
                component: agregarIngreso,
                meta: {
                    breadcrumb: [{
                            label: "Operaciones",
                            type: "fixed",
                            path: "/operaciones"
                        },
                        {
                            label: "Ingresos",
                            type: "fixed",
                            path: "/operaciones/ingreso"
                        },
                        {
                            label: "Agregar",
                            type: "fixed",
                            path: "/operaciones/ingreso/agregar",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'ingreso',
                path: 'operaciones/ingreso/:id',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                            label: "Operaciones",
                            type: "fixed",
                            path: "/operaciones"
                        },
                        {
                            label: "Ingresos",
                            type: "fixed",
                            path: "/operaciones/ingreso"
                        },
                        {
                            label: "id",
                            type: "dynamic",
                            path: "/operaciones/ingreso/id",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'egresos',
                path: 'operaciones/egreso',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                            label: "Operaciones",
                            type: "fixed",
                            path: "/operaciones"
                        },
                        {
                            label: "Egresos",
                            type: "fixed",
                            path: "/operaciones/egreso",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'agregarEgreso',
                path: 'operaciones/egreso/agregar',
                component: agregarEgreso,
                meta: {
                    breadcrumb: [{
                            label: "Operaciones",
                            type: "fixed",
                            path: "/operaciones"
                        },
                        {
                            label: "Egresos",
                            type: "fixed",
                            path: "/operaciones/egreso"
                        },
                        {
                            label: "Agregar",
                            type: "fixed",
                            path: "/operaciones/egreso/agregar",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'egreso',
                path: 'operaciones/egreso/:id',
                component: operaciones,
                meta: {
                    breadcrumb: [{
                            label: "Operaciones",
                            type: "fixed",
                            path: "/operaciones"
                        },
                        {
                            label: "Egresos",
                            type: "fixed",
                            path: "/operaciones/egreso"
                        },
                        {
                            label: "id",
                            type: "dynamic",
                            path: "/operaciones/egreso/id",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'bandeja',
                path: 'bandeja',
                component: bandeja,
                meta: {
                    breadcrumb: [{
                        label: "Bandeja de mensajes",
                        type: "fixed",
                        path: "/bandeja",
                        active: true
                    }]
                }
            },
            {
                name: 'mensaje',
                path: 'bandeja/:id',
                component: bandeja,
                meta: {
                    breadcrumb: [{
                            label: "Bandeja de mensajes",
                            type: "fixed",
                            path: "/bandeja"
                        },
                        {
                            label: "id",
                            type: "dynamic",
                            path: "/bandeja/id",
                            active: true
                        }
                    ]
                }
            },
            {
                name: 'configuracion',
                path: 'configuracion',
                component: configuracion,
                meta: {
                    breadcrumb: [{
                        label: "Configuracion",
                        type: "fixed",
                        path: "/configuracion",
                        active: true
                    }]
                },
                children: [
                    {
                        name: 'configUsuario',
                        path: 'usuario',
                        component: configUsuario,
                        meta: {
                            breadcrumb: [{
                                    label: "Configuracion",
                                    type: "fixed",
                                    path: "/configuracion"
                                },
                                {
                                    label: "Mi Usuario",
                                    type: "fixed",
                                    path: "/configuracion/usuario",
                                    active: true
                                }
                            ]
                        }
                    },
                    {
                        name: 'configValidacion',
                        path: 'validacion',
                        component: configValidacion,
                        meta: {
                            breadcrumb: [{
                                    label: "Configuracion",
                                    type: "fixed",
                                    path: "/configuracion"
                                },
                                {
                                    label: "Validacion de Egreso",
                                    type: "fixed",
                                    path: "/configuracion/validacion",
                                    active: true
                                }
                            ]
                        }
                    }
                ]
            }
        ]
    },
    {
        name: 'login',
        path: '/login',
        component: login
    },
    {
        name: 'error404',
        path: '/*',
        component: error404
    }
];*/