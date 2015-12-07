'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eventScore', {
                parent: 'entity',
                url: '/eventScores',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'tiimspotApp.eventScore.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventScore/eventScores.html',
                        controller: 'EventScoreController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventScore');
                        $translatePartialLoader.addPart('score');
                        $translatePartialLoader.addPart('score');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eventScore.detail', {
                parent: 'entity',
                url: '/eventScore/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'tiimspotApp.eventScore.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventScore/eventScore-detail.html',
                        controller: 'EventScoreDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventScore');
                        $translatePartialLoader.addPart('score');
                        $translatePartialLoader.addPart('score');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EventScore', function($stateParams, EventScore) {
                        return EventScore.get({id : $stateParams.id});
                    }]
                }
            })
            .state('eventScore.new', {
                parent: 'eventScore',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eventScore/eventScore-dialog.html',
                        controller: 'EventScoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    discipline: null,
                                    techniek: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('eventScore', null, { reload: true });
                    }, function() {
                        $state.go('eventScore');
                    })
                }]
            })
            .state('eventScore.edit', {
                parent: 'eventScore',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eventScore/eventScore-dialog.html',
                        controller: 'EventScoreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EventScore', function(EventScore) {
                                return EventScore.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('eventScore', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('eventScore.delete', {
                parent: 'eventScore',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eventScore/eventScore-delete-dialog.html',
                        controller: 'EventScoreDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['EventScore', function(EventScore) {
                                return EventScore.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('eventScore', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
