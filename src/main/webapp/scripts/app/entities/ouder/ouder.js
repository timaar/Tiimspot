'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ouder', {
                parent: 'entity',
                url: '/ouders',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.ouder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ouder/ouders.html',
                        controller: 'OuderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ouder');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ouder.detail', {
                parent: 'entity',
                url: '/ouder/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.ouder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ouder/ouder-detail.html',
                        controller: 'OuderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ouder');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Ouder', function($stateParams, Ouder) {
                        return Ouder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ouder.new', {
                parent: 'ouder',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ouder/ouder-dialog.html',
                        controller: 'OuderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ouder', null, { reload: true });
                    }, function() {
                        $state.go('ouder');
                    })
                }]
            })
            .state('ouder.edit', {
                parent: 'ouder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ouder/ouder-dialog.html',
                        controller: 'OuderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ouder', function(Ouder) {
                                return Ouder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ouder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ouder.delete', {
                parent: 'ouder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ouder/ouder-delete-dialog.html',
                        controller: 'OuderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Ouder', function(Ouder) {
                                return Ouder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ouder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
