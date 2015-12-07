'use strict';

angular.module('tiimspotApp')
    .controller('PersoonEventController', function ($scope, $state, $modal, PersoonEvent, PersoonEventSearch, ParseLinks) {
      
        $scope.persoonEvents = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PersoonEvent.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.persoonEvents.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.persoonEvents = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PersoonEventSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.persoonEvents = result;
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
            $scope.persoonEvent = {
                aanwezigheidsStatus: null,
                id: null
            };
        };
    });
