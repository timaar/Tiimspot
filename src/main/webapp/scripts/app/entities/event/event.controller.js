'use strict';

angular.module('tiimspotApp')
    .controller('EventController', function ($scope, $state, $modal, Event, EventSearch, ParseLinks) {
      
        $scope.events = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Event.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.events.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.events = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            EventSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.events = result;
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
            $scope.event = {
                naam: null,
                datum: null,
                type: null,
                id: null
            };
        };
    });
