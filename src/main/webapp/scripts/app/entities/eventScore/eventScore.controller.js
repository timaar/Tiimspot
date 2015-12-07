'use strict';

angular.module('tiimspotApp')
    .controller('EventScoreController', function ($scope, $state, $modal, EventScore, EventScoreSearch, ParseLinks) {
      
        $scope.eventScores = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EventScore.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.eventScores.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.eventScores = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            EventScoreSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.eventScores = result;
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
            $scope.eventScore = {
                discipline: null,
                techniek: null,
                id: null
            };
        };
    });
