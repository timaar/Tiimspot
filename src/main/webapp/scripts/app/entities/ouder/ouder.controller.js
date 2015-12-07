'use strict';

angular.module('tiimspotApp')
    .controller('OuderController', function ($scope, $state, $modal, Ouder, OuderSearch, ParseLinks) {
      
        $scope.ouders = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Ouder.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.ouders.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.ouders = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            OuderSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.ouders = result;
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
            $scope.ouder = {
                id: null
            };
        };
    });
