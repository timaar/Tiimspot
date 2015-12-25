'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('persoonEvent', {
                parent: 'entity',
                url: '/persoonEvents',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.persoonEvent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/persoonEvent/persoonEvents.html',
                        controller: 'PersoonEventController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('persoonEvent');
                        $translatePartialLoader.addPart('aanwezigheidsStatus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('persoonEvent.detail', {
                parent: 'entity',
                url: '/persoonEvent/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.persoonEvent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/persoonEvent/persoonEvent-detail.html',
                        controller: 'PersoonEventDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('persoonEvent');
                        $translatePartialLoader.addPart('aanwezigheidsStatus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PersoonEvent', function($stateParams, PersoonEvent) {
                        return PersoonEvent.get({id : $stateParams.id});
                    }]
                }
            })
            .state('persoonEvent.new', {
                parent: 'persoonEvent',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/persoonEvent/persoonEvent-dialog.html',
                        controller: 'PersoonEventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    aanwezigheidsStatus: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('persoonEvent', null, { reload: true });
                    }, function() {
                        $state.go('persoonEvent');
                    })
                }]
            })
            .state('persoonEvent.edit', {
                parent: 'persoonEvent',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/persoonEvent/persoonEvent-dialog.html',
                        controller: 'PersoonEventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PersoonEvent', function(PersoonEvent) {
                                return PersoonEvent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('persoonEvent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('persoonEvent.delete', {
                parent: 'persoonEvent',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/persoonEvent/persoonEvent-delete-dialog.html',
                        controller: 'PersoonEventDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PersoonEvent', function(PersoonEvent) {
                                return PersoonEvent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('persoonEvent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
