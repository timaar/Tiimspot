'use strict';

angular.module('tiimspotApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('comment', {
                parent: 'entity',
                url: '/comments',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.comment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comment/comments.html',
                        controller: 'CommentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('comment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('comment.detail', {
                parent: 'entity',
                url: '/comment/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'tiimspotApp.comment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comment/comment-detail.html',
                        controller: 'CommentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('comment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Comment', function($stateParams, Comment) {
                        return Comment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('comment.new', {
                parent: 'comment',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/comment/comment-dialog.html',
                        controller: 'CommentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    comment: null,
                                    creationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('comment', null, { reload: true });
                    }, function() {
                        $state.go('comment');
                    })
                }]
            })
            .state('comment.edit', {
                parent: 'comment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/comment/comment-dialog.html',
                        controller: 'CommentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Comment', function(Comment) {
                                return Comment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('comment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('comment.delete', {
                parent: 'comment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/comment/comment-delete-dialog.html',
                        controller: 'CommentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Comment', function(Comment) {
                                return Comment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('comment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
