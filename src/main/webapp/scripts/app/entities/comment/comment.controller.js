'use strict';

angular.module('tiimspotApp')
    .controller('CommentController', function ($scope, $state, $modal, Comment, CommentSearch, ParseLinks) {
      
        $scope.comments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Comment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.comments.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.comments = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CommentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.comments = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.comment = {
                comment: null,
                creationDate: null,
                id: null
            };
        };
    });
