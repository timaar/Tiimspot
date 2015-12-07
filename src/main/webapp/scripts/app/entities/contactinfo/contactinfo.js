'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contactinfo', {
                parent: 'entity',
                url: '/contactinfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'tiimspotApp.contactinfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contactinfo/contactinfos.html',
                        controller: 'ContactinfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactinfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contactinfo.detail', {
                parent: 'entity',
                url: '/contactinfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'tiimspotApp.contactinfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contactinfo/contactinfo-detail.html',
                        controller: 'ContactinfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactinfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Contactinfo', function($stateParams, Contactinfo) {
                        return Contactinfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contactinfo.new', {
                parent: 'contactinfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contactinfo/contactinfo-dialog.html',
                        controller: 'ContactinfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    email: null,
                                    telefoonnummer: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contactinfo', null, { reload: true });
                    }, function() {
                        $state.go('contactinfo');
                    })
                }]
            })
            .state('contactinfo.edit', {
                parent: 'contactinfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contactinfo/contactinfo-dialog.html',
                        controller: 'ContactinfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contactinfo', function(Contactinfo) {
                                return Contactinfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contactinfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contactinfo.delete', {
                parent: 'contactinfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/contactinfo/contactinfo-delete-dialog.html',
                        controller: 'ContactinfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Contactinfo', function(Contactinfo) {
                                return Contactinfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contactinfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
