'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('adres', {
                parent: 'entity',
                url: '/adress',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'tiimspotApp.adres.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adres/adress.html',
                        controller: 'AdresController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('adres');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('adres.detail', {
                parent: 'entity',
                url: '/adres/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'tiimspotApp.adres.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adres/adres-detail.html',
                        controller: 'AdresDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('adres');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Adres', function($stateParams, Adres) {
                        return Adres.get({id : $stateParams.id});
                    }]
                }
            })
            .state('adres.new', {
                parent: 'adres',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/adres/adres-dialog.html',
                        controller: 'AdresDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    straat: null,
                                    huisnummer: null,
                                    busnummer: null,
                                    postcode: null,
                                    gemeente: null,
                                    landISO3: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('adres', null, { reload: true });
                    }, function() {
                        $state.go('adres');
                    })
                }]
            })
            .state('adres.edit', {
                parent: 'adres',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/adres/adres-dialog.html',
                        controller: 'AdresDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Adres', function(Adres) {
                                return Adres.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adres', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('adres.delete', {
                parent: 'adres',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/adres/adres-delete-dialog.html',
                        controller: 'AdresDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Adres', function(Adres) {
                                return Adres.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adres', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
