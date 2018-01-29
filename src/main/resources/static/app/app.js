(function(angular) {
    var app = angular.module('cloudAdmin', 
        ['ui.router', 'ncy-angular-breadcrumb', 'core-ui', 'JsUI']);
    
    app.constant('appConfig', {
            baseUrl: 'app/',
            apiUrl: 'api/',
            html : function(val) {
                return this.baseUrl + val;
            },
            rest : function () {
                var result = this.apiUrl,
                    i, val, split = '';

                for (i = 0; i < arguments.length; ++i) {
                    val = arguments[i];
                    if (angular.isDefined(val)) {
                        result += split + arguments[i];
                        split = '/';
                    }
                }
                return result;
            }
        })
        .config(['appConfig', '$urlRouterProvider', '$stateProvider', '$breadcrumbProvider', '$jsuiStateProvider',
            function(appConfig, $urlRouterProvider, $stateProvider, $breadcrumbProvider, $jsuiStateProvider) {

            $breadcrumbProvider.setOptions({
                prefixStateName: 'app.connections',
                includeAbstract: true,
                template: '<li class="breadcrumb-item" ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract"><a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a><span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span></li>'
            });
            
            // https://github.com/angular-ui/ui-router/wiki
            $urlRouterProvider.otherwise('/connections');
            // URL States normal ...
            $stateProvider.state('app', {
                abstract: true,
                templateUrl: appConfig.html('layouts/full.html'),
                ncyBreadcrumb: {
                    label: 'Root',
                    skip: true
                }
            })
            .state('app.connections', {
                url: '/connections',
                component: 'dbConnections',
                ncyBreadcrumb: {
                    label: 'DB Connections'
                }
            }).state('app.db', {
                url: '/connections/:dbId',
                params: {
                    dbId: null,
                    db: null
                },
                component: 'dbConnectionSettings',
                ncyBreadcrumb: {
                    parent: 'app.connections',
                    label: '{{ pageLabel || "New DB Connection" }}'
                },
                resolve: { 
                    dbId: function($stateParams) { return $stateParams.dbId; },
                    db: function($stateParams) { 
                        return $stateParams.db; 
                    }
                }
            })
            .state('app.sql', {
                url: '/sql',
                component: 'sqlEditor',
                ncyBreadcrumb: {
                    label: 'SQL Editor'
                }
            })
            ;
            
            app.component('home', {
                controller: function () {
                },
                templateUrl: ['appConfig', function(appConfig) {
                    return appConfig.html('home/home.html');
                }]
            });
    }]);
})(angular);