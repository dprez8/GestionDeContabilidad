<template>
    <div id="sidebar-wrapper" class="border-right">
        <b-list-group flush>
            <template v-for="(item, key) in sidebar">
                <b-list-group-item 
                    :key="key"
                    class="d-flex align-items-center" 
                    :to="item.to" 
                    :active="active(item)"
                >
                    <b-icon class="mr-2" :icon="item.icon"></b-icon>{{item.text}}
                </b-list-group-item>
            </template>
        </b-list-group>
    </div>
</template>

<script>
export default {
    props: {
        sidebar: Array
    },
    methods: {
        closeSidebar() {
            this.$emit('closeSidebar');
        },
        active(item) {
            if(this.$route.meta.breadcrumb && this.$route.meta.breadcrumb[0]) {
                return this.$route.meta.breadcrumb[0].label == item.text;
            } else {
                return this.$route.name == item.routeName;
            }
            
        }
    },
}
</script>

<style>
#wrapper {
    overflow-x: hidden;
}

#sidebar-wrapper {
    min-height: calc(100vh - 57px);
    margin-left: -15rem;
    -webkit-transition: margin .25s ease-out;
    -moz-transition: margin .25s ease-out;
    -o-transition: margin .25s ease-out;
    transition: margin .25s ease-out;
}

#sidebar-wrapper .sidebar-heading {
    padding: 0.875rem 1.25rem;
    font-size: 1.2rem;
}

#sidebar-wrapper .list-group {
    width: 15rem;
}

#page-content-wrapper {
    min-width: 100vw;
}

#wrapper.toggled #sidebar-wrapper {
    margin-left: 0;
}

@media (min-width: 768px) {
    #sidebar-wrapper {
        margin-left: 0;
    }

    #page-content-wrapper {
        min-width: 0;
        width: 100%;
    }

    #wrapper.toggled #sidebar-wrapper {
        margin-left: -15rem;
    }
    #navbar-organizacion{
        width: 14rem;
    }
}
</style>