<template>
    <b-breadcrumb class="m-0 border-bottom bg-light" style="border-radius: 0;">
        <b-breadcrumb-item to="/">
            <b-icon-house></b-icon-house>
        </b-breadcrumb-item>
        <template v-for="(page, index) in breadcrumb">
            <b-breadcrumb-item :key=index v-bind:to="page.path" v-bind:active="page.active">
                {{ page.label }}
            </b-breadcrumb-item>
        </template>
    </b-breadcrumb>
</template>

<script>
export default {
    data() {
        return {
            breadcrumb: []
        }
    },
    methods: {
        updateBreadcrumb() {
            
            if(this.$route.meta.breadcrumb == undefined){
                this.breadcrumb = [];
                return;
            }
            // copiar el objeto
            var breadcrumb = JSON.parse(JSON.stringify(this.$route.meta.breadcrumb));

            breadcrumb.forEach(crumb => {
                if(crumb.type == "dynamic") {
                    var key = crumb.dynamicText;
                    var dynamicLabel = this.$route.params[key];

                    if(dynamicLabel != undefined) {
                        crumb.label = crumb.label.replace(key, dynamicLabel);
                        crumb.path = crumb.path.replace(key, dynamicLabel);
                    }
                }
            });
            this.breadcrumb = breadcrumb;
        }
    },
    watch: {
        $route(to, from) {
            this.updateBreadcrumb();
        }
    },
    mounted() {
        this.updateBreadcrumb();
    }
}
</script>

<style>

</style>