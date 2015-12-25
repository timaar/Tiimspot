'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('persoon', {
                parent: 'entity',
                url: '/persoons',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.persoon.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/persoon/persoons.html',
                        controller: 'PersoonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('persoon');
                        $translatePartialLoader.addPart('geslacht');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('persoon.detail', {
                parent: 'entity',
                url: '/persoon/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.persoon.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/persoon/persoon-detail.html',
                        controller: 'PersoonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('persoon');
                        $translatePartialLoader.addPart('geslacht');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Persoon', function($stateParams, Persoon) {
                        return Persoon.get({id : $stateParams.id});
                    }]
                }
            })
            .state('persoon.new', {
                parent: 'persoon',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/persoon/persoon-dialog.html',
                        controller: 'PersoonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    voornaam: null,
                                    naam: null,
                                    geslacht: null,
                                    geboorteDatum: null,
                                    telefoonnummer: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('persoon', null, { reload: true });
                    }, function() {
                        $state.go('persoon');
                    })
                }]
            })
            .state('persoon.edit', {
                parent: 'persoon',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/persoon/persoon-dialog.html',
                        controller: 'PersoonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Persoon', function(Persoon) {
                                return Persoon.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('persoon', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('persoon.delete', {
                parent: 'persoon',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/persoon/persoon-delete-dialog.html',
                        controller: 'PersoonDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Persoon', function(Persoon) {
                                return Persoon.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('persoon', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
